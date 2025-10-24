"""
Agent-oriented BPEL analyzer scaffold.

This module provides a pluggable interface to analyze BPEL using either:
- A regex/XML parser (fast, deterministic)
- A local LLM agent (via Ollama + LangGraph), when available

Current implementation defaults to regex/XML and will attempt LLM
if dependencies are installed, otherwise it logs and falls back.
"""
from __future__ import annotations
import os
import json
from pathlib import Path
from dataclasses import dataclass, asdict
from typing import Any, Dict, List
import xml.etree.ElementTree as ET
import re
import warnings

# Suppress noisy ResourceWarning across agent operations
warnings.simplefilter("ignore", ResourceWarning)

try:
    import ollama
    OLLAMA_AVAILABLE = True
except ImportError:
    OLLAMA_AVAILABLE = False

BPWS = "http://schemas.xmlsoap.org/ws/2004/03/business-process/"

@dataclass
class Invoke:
    name: str
    operation: str
    partnerLink: str

@dataclass
class InboundOp:
    operation: str
    partnerLink: str
    portType: str
    invokes: List[Invoke]
    catches: List[Dict[str, str]]
    replyFaults: List[Dict[str, str]]
    assigns: List[Dict[str, str]]

@dataclass
class BpelAnalysis:
    process_name: str
    partner_links: List[Dict[str, str]]
    inbound_ops: List[InboundOp]
    variables: List[Dict[str, str]]


class RegexXmlAnalyzer:
    """Legacy regex-based analyzer - kept for emergency fallback only."""
    def analyze(self, bpel_path: Path) -> BpelAnalysis:
        text = bpel_path.read_text(encoding="utf-8", errors="ignore")
        root = ET.fromstring(text)
        process_name = root.get("name", bpel_path.stem)
        partner_links = []
        for pl in root.findall(f".//{{{BPWS}}}partnerLink"):
            partner_links.append({
                "name": pl.get("name", pl.get("myRole", "")),
                "myRole": pl.get("myRole", ""),
                "partnerRole": pl.get("partnerRole", ""),
                "type": pl.get("partnerLinkType", ""),
            })
        # Variables
        variables: List[Dict[str, str]] = []
        for v in root.findall(f".//{{{BPWS}}}variable"):
            variables.append({
                "name": v.get("name", ""),
                "type": v.get("type", ""),
                "messageType": v.get("messageType", ""),
                "element": v.get("element", ""),
            })
        picks = root.findall(f".//{{{BPWS}}}pick")
        inbound_meta = []
        for pick in picks:
            for on in pick.findall(f".//{{{BPWS}}}onMessage"):
                inbound_meta.append({
                    "operation": on.get("operation", ""),
                    "partnerLink": on.get("partnerLink", ""),
                    "portType": on.get("portType", ""),
                })
        # Regex-based extraction within each onMessage block
        on_msgs = []
        for match in re.finditer(r'<bpws:onMessage[^>]*operation="([^"]*)"[^>]*>(.*?)</bpws:onMessage>', text, re.DOTALL):
            operation = match.group(1)
            block = match.group(2)
            invokes = []
            for inv_match in re.finditer(r'<bpws:invoke[^>]*name="([^"]*)"[^>]*operation="([^"]*)"[^>]*partnerLink="([^"]*)"', block):
                invokes.append({"name": inv_match.group(1), "operation": inv_match.group(2), "partnerLink": inv_match.group(3)})
            catches = []
            for catch_match in re.finditer(r'<bpws:catch[^>]*faultName="([^"]*)"[^>]*faultMessageType="([^"]*)"[^>]*faultVariable="([^"]*)"', block):
                catches.append({"faultName": catch_match.group(1), "faultMessageType": catch_match.group(2), "faultVariable": catch_match.group(3)})
            reply_faults = []
            for rf_match in re.finditer(r'<bpws:reply[^>]*faultName="([^"]*)"[^>]*operation="([^"]*)"', block):
                reply_faults.append({"faultName": rf_match.group(1), "operation": rf_match.group(2)})
            on_msgs.append({"operation": operation, "invokes": invokes, "catches": catches, "replyFaults": reply_faults})
        inbound_ops = []
        for i, _ in enumerate(on_msgs):
            invokes = [
                Invoke(name=inv["name"], operation=inv["operation"], partnerLink=inv["partnerLink"])
                for inv in on_msgs[i]["invokes"]
            ]
            catches = on_msgs[i]["catches"]
            reply_faults = on_msgs[i]["replyFaults"]
            meta = next((m for m in inbound_meta if m["operation"] == on_msgs[i]["operation"]), {})
            inbound_ops.append(InboundOp(
                operation=on_msgs[i]["operation"],
                partnerLink=meta.get("partnerLink", ""),
                portType=meta.get("portType", ""),
                invokes=invokes,
                catches=catches,
                replyFaults=reply_faults,
            ))
        return BpelAnalysis(process_name=process_name, partner_links=partner_links, inbound_ops=inbound_ops, variables=variables)


class LlmAgentAnalyzer:
    """Professional BPEL analyzer using local qwen2.5-coder models."""
    
    def __init__(self):
        if not OLLAMA_AVAILABLE:
            raise ImportError("ollama package is required for LLM-based BPEL analysis. Install with: pip install ollama")
        
        # Initialize model selection without holding a persistent client
        self.model = self._select_model()

    def _select_model(self) -> str:
        """Select the best available qwen2.5-coder model."""
        try:
            models_resp = ollama.list()
            models_list = models_resp.get('models', [])
            available_models = []
            for m in models_list:
                # Support both 'model' and 'name' keys depending on Ollama client version
                val = m.get('model') or m.get('name')
                if val:
                    available_models.append(val)
            
            # Priority order: qwen3-coder:latest -> qwen2.5-coder:latest -> 1.5b-base
            preferred_models = [
                "qwen3-coder:latest",
                "qwen2.5-coder:latest",
                "qwen2.5-coder:1.5b-base"
            ]
            
            for model in preferred_models:
                if model in available_models:
                    print(f"[LlmAgentAnalyzer] Using model: {model}")
                    return model
                    
            # If exact matches not found, look for qwen2.5-coder variants
            qwen_models = [m for m in available_models if 'qwen2.5-coder' in m.lower()]
            if qwen_models:
                model = qwen_models[0]
                print(f"[LlmAgentAnalyzer] Using available qwen2.5-coder variant: {model}")
                return model
                
            raise RuntimeError(
                f"No suitable qwen2.5-coder model found. Available models: {available_models}\n"
                "Please install one of the required models:\n"
                "  ollama pull qwen2.5-coder:latest\n"
                "  ollama pull qwen2.5-coder:1.5b-base"
            )
            
        except Exception as e:
            raise RuntimeError(f"Failed to connect to Ollama or list models: {e}")

    def _create_analysis_prompt(self, bpel_content: str) -> str:
        """Create a sophisticated prompt for BPEL analysis."""
        return f"""You are a professional BPEL (Business Process Execution Language) analyzer. 
Analyze the following BPEL file and extract structured information with high precision.

BPEL Content:
```xml
{bpel_content}
```

Please analyze this BPEL file and return a JSON response with the following structure:

{{
  "process_name": "string - the name attribute of the process element",
  "partner_links": [
    {{
      "name": "string - partnerLink name",
      "myRole": "string - myRole attribute or empty",
      "partnerRole": "string - partnerRole attribute or empty", 
      "type": "string - partnerLinkType attribute"
    }}
  ],
  "variables": [
    {{
      "name": "string - variable name",
      "type": "string - type attribute or empty",
      "messageType": "string - messageType attribute or empty",
      "element": "string - element attribute or empty"
    }}
  ],
  "inbound_ops": [
    {{
      "operation": "string - operation name from onMessage",
      "partnerLink": "string - partnerLink from onMessage",
      "portType": "string - portType from onMessage", 
      "invokes": [
        {{
          "name": "string - invoke name attribute",
          "operation": "string - invoke operation",
          "partnerLink": "string - invoke partnerLink"
        }}
      ],
      "catches": [
        {{
          "faultName": "string - fault name",
          "faultMessageType": "string - fault message type",
          "faultVariable": "string - fault variable name"
        }}
      ],
      "replyFaults": [
        {{
          "faultName": "string - reply fault name", 
          "operation": "string - reply operation"
        }}
      ],
      "assigns": [
        {{
          "fromVar": "string - variable in bpws:from (or empty)",
          "fromPart": "string - message part in bpws:from (or empty)",
          "fromXpath": "string - xpath or query in bpws:from (or empty)",
          "toVar": "string - variable in bpws:to",
          "toPart": "string - message part in bpws:to (or empty)",
          "toXpath": "string - xpath or query in bpws:to (or empty)",
          "expression": "string - expression used if present (or empty)",
          "literal": "string - literal value if present (or empty)",
          "note": "string - brief description of mapping purpose (optional)"
        }}
      ]
    }}
  ]
}}

IMPORTANT INSTRUCTIONS:
1. Extract ALL partner links, variables, and inbound operations
2. For each onMessage, find ALL invoke activities within that operation's scope
3. EXCLUDE invoke operations with operation=\"null\" or partnerLink=\"null\" - these are internal scripts, not service calls
4. ONLY include invoke operations that call external services (non-null operation and partnerLink)
5. Extract ALL catch blocks and reply fault activities
6. Extract ALL bpws:assign blocks and their bpws:copy entries. For each copy, resolve bpws:from and bpws:to to variables, parts, and any xpath/query expressions.
7. If explicit bpws:assign are absent, infer simple mapping intents from nearby variable manipulations or script blocks (e.g., wpc:script/javaCode) when clearly implied. Be conservative; prefer empty list over guessing.
8. Ensure JSON is valid and properly formatted
9. Use empty strings for missing attributes, never null
10. Be precise with XML namespace handling (bpws: prefix)
11. Focus on the logical flow and sequence of operations
12. Return ONLY the JSON response, no additional text

Analyze carefully and provide the complete structured data."""

    def analyze(self, bpel_path: Path) -> BpelAnalysis:
        """Analyze BPEL file using local LLM with sophisticated prompting."""
        try:
            bpel_content = bpel_path.read_text(encoding="utf-8", errors="ignore")
            prompt = self._create_analysis_prompt(bpel_content)
            
            print(f"[LlmAgentAnalyzer] Analyzing {bpel_path.name} with {self.model}...")
            
            response = ollama.generate(
                model=self.model,
                prompt=prompt,
                options={
                    "temperature": 0.1,  # Low temperature for consistent, precise analysis
                    "top_p": 0.9,
                    "num_predict": 4096,  # Allow longer responses for complex BPEL
                },
                stream=False
            )
            
            response_text = response['response'].strip()
            
            # Extract JSON from response (handle cases where LLM adds extra text)
            json_start = response_text.find('{')
            json_end = response_text.rfind('}') + 1
            
            if json_start == -1 or json_end == 0:
                raise ValueError("No valid JSON found in LLM response")
                
            json_text = response_text[json_start:json_end]
            
            try:
                analysis_data = json.loads(json_text)
            except json.JSONDecodeError as e:
                print(f"[LlmAgentAnalyzer] JSON decode error: {e}")
                print(f"[LlmAgentAnalyzer] Raw response: {response_text[:500]}...")
                raise ValueError(f"Invalid JSON in LLM response: {e}")
            
            # Convert to BpelAnalysis dataclass
            inbound_ops = []
            for op_data in analysis_data.get('inbound_ops', []):
                invokes = [
                    Invoke(
                        name=inv.get('name', ''),
                        operation=inv.get('operation', ''),
                        partnerLink=inv.get('partnerLink', '')
                    )
                    for inv in op_data.get('invokes', [])
                ]
                
                inbound_ops.append(InboundOp(
                    operation=op_data.get('operation', ''),
                    partnerLink=op_data.get('partnerLink', ''),
                    portType=op_data.get('portType', ''),
                    invokes=invokes,
                    catches=op_data.get('catches', []),
                    replyFaults=op_data.get('replyFaults', []),
                    assigns=op_data.get('assigns', [])
                ))
            
            result = BpelAnalysis(
                process_name=analysis_data.get('process_name', bpel_path.stem),
                partner_links=analysis_data.get('partner_links', []),
                inbound_ops=inbound_ops,
                variables=analysis_data.get('variables', [])
            )
            
            print(f"[LlmAgentAnalyzer] Successfully analyzed {len(result.inbound_ops)} inbound operations")
            return result
            
        except Exception as e:
            print(f"[LlmAgentAnalyzer] Analysis failed: {e}")
            raise RuntimeError(f"LLM-based BPEL analysis failed: {e}")


class BpelAgent:
    """Professional BPEL analysis agent using local LLM models."""
    
    def __init__(self):
        self.llm = LlmAgentAnalyzer()
        # Keep regex analyzer for emergency fallback only (not used by default)
        self.regex = RegexXmlAnalyzer()

    def analyze(self, bpel_path: Path) -> BpelAnalysis:
        """Analyze BPEL file using LLM-based approach."""
        return self.llm.analyze(bpel_path)
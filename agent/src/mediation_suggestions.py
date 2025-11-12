import json
from typing import Any, Dict, List, Tuple


def suggest_mediation_branches(plan: Dict[str, Any]) -> Tuple[Dict[str, Any], List[str]]:
    """
    Produce suggestions for complex mediation branches from an orchestration plan.
    Returns (suggestions_dict, stub_route_lines) where suggestions include a reviewer gate.
    """
    ops = plan.get("operations") or plan.get("sequence") or []
    suggestions: Dict[str, Any] = {
        "review_required": True,
        "branches": [],
        "fallbacks": [],
    }
    stub_lines: List[str] = [
        "# mediation-suggestions-stub.yaml",
        "# REVIEW_REQUIRED: true",
        "- route:",
        "    id: mediation-suggestions-stub",
        "    from:",
        "      uri: direct:mediation-suggestions",
        "    steps:",
        "      - log: 'Applying suggested mediation stubs'",
    ]

    # Heuristic: if operations mention condition or branch, create suggestions
    for idx, op in enumerate(ops):
        name = (op.get("name") or op.get("operation") or f"op_{idx}").lower()
        condition = op.get("condition") or op.get("branch_condition")
        suggestion = {
            "operation": name,
            "suggested_logic": None,
            "confidence": 0.5,
        }
        if condition:
            suggestion["suggested_logic"] = f"if ({condition}) then route to helper; else passthrough"
            suggestion["confidence"] = 0.7
            stub_lines += [
                "      - choice:",
                f"          - when: '{{{{ {condition} }}}}'",
                "              steps:",
                "                - to: direct:helper-route",
                "          - otherwise:",
                "              steps:",
                "                - set-body:",
                "                    simple: ${body}",
            ]
        else:
            # Fallback deterministic stub
            suggestion["suggested_logic"] = "deterministic passthrough with logging"
            suggestions["fallbacks"].append(name)
            stub_lines += [
                "      - set-body:",
                "          simple: ${body}",
            ]
        suggestions["branches"].append(suggestion)

    return suggestions, stub_lines


def suggestions_to_markdown(suggestions: Dict[str, Any]) -> str:
    lines = ["# Mediation Suggestions", "", f"Reviewer Gate: {'REQUIRED' if suggestions.get('review_required') else 'OPTIONAL'}", ""]
    for s in suggestions.get("branches", []):
        lines.append(f"- Operation: {s['operation']} | Confidence: {s['confidence']}")
        lines.append(f"  Suggested: {s['suggested_logic']}")
    if suggestions.get("fallbacks"):
        lines.append("")
        lines.append("Fallback deterministic stubs:")
        for f in suggestions["fallbacks"]:
            lines.append(f"- {f}")
    return "\n".join(lines).strip()


__all__ = ["suggest_mediation_branches", "suggestions_to_markdown"]
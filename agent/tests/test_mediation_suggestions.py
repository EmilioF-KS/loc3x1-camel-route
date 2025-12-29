from agent.src.mediation_suggestions import suggest_mediation_branches, suggestions_to_markdown


def test_mediation_suggestions_include_reviewer_gate_and_fallbacks():
    plan = {
        "operations": [
            {"name": "CheckEligibility", "condition": "${header.eligible} == true"},
            {"name": "UnknownBranchLogic"},
        ]
    }
    suggestions, stub_lines = suggest_mediation_branches(plan)
    # Reviewer gate required
    assert suggestions["review_required"] is True
    # First branch has a suggested logic with higher confidence
    assert any("checkeligibility" in b["operation"] and b["suggested_logic"] for b in suggestions["branches"])
    # Fallback exists for the unknown branch
    assert "unknownbranchlogic" in suggestions["fallbacks"]
    # Stub YAML lines contain choice and set-body steps
    assert any("choice:" in l for l in stub_lines)
    assert any("set-body:" in l for l in stub_lines)


def test_mediation_suggestions_markdown_contains_required_text():
    plan = {"operations": [{"operation": "RouteA"}, {"operation": "RouteB", "condition": "${body} != null"}]}
    suggestions, _ = suggest_mediation_branches(plan)
    md = suggestions_to_markdown(suggestions)
    assert "Reviewer Gate: REQUIRED" in md
    assert "Fallback deterministic stubs:" in md
    assert "Operation: routea" in md
    assert "Suggested:" in md
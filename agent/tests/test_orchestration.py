import json
from pathlib import Path

from agent.src.orchestration import build_orchestration_plan


def test_bpel_parsing_synthetic(tmp_path: Path):
    bpel = tmp_path / "TestProcess.bpel"
    bpel.write_text(
        """
        <process xmlns="http://docs.oasis-open.org/wsbpel/2.0/process/executable">
          <receive partnerLink="Client" operation="start" variable="req"/>
          <assign name="prepare">
            <copy />
          </assign>
          <invoke partnerLink="Provider" portType="ns:Port" operation="do" inputVariable="in" outputVariable="out"/>
          <scope>
            <faultHandlers>
              <catch faultName="ns:BusinessFault"/>
            </faultHandlers>
          </scope>
        </process>
        """
    )

    manifest = {
        "artifacts": [
            {"type": "bpel", "abs_path": str(bpel)},
        ]
    }
    plan = build_orchestration_plan(manifest)
    assert plan["summary"]["bpel_count"] == 1
    steps = plan["bpel"][0]["steps"]
    kinds = [s["kind"] for s in steps]
    assert "receive" in kinds and "assign" in kinds and "invoke" in kinds
    assert any(s.get("kind") == "faultHandlers" for s in steps)


def test_mediation_parsing_synthetic(tmp_path: Path):
    med = tmp_path / "Test_mediation.xml"
    med.write_text(
        """
        <flow>
          <node mapRef="maps/MainMap" />
          <invoke name="CallService" />
          <log>info</log>
        </flow>
        """
    )

    manifest = {
        "artifacts": [
            {"type": "ibm_mediation", "abs_path": str(med)},
        ]
    }
    plan = build_orchestration_plan(manifest)
    assert plan["summary"]["mediation_count"] == 1
    steps = plan["mediation"][0]["steps"]
    kinds = [s["kind"] for s in steps]
    assert "transform" in kinds and "invoke" in kinds and "log" in kinds
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel
from uuid import uuid4
from ..services.orchestrator import start_run, get_status
from fastapi.responses import StreamingResponse
import os
import zipfile
import tempfile

router = APIRouter()

class CreateRunBody(BaseModel):
    inputPath: str

@router.post('/runs')
def create_run(body: CreateRunBody):
    if not body.inputPath:
        raise HTTPException(status_code=400, detail='inputPath required')
    run_id = str(uuid4())
    start_run(run_id, body.inputPath)
    return {"id": run_id, "status": "queued"}

@router.get('/archive/{run_id}')
def archive(run_id: str):
    s = get_status(run_id)
    if not s or not isinstance(s, dict) or not s.get("results"):
        raise HTTPException(status_code=404, detail='run not found')
    out_main = s["results"].get("output")
    out_mock = s["results"].get("mock")
    if not out_main or not os.path.isdir(out_main):
        raise HTTPException(status_code=404, detail='output not found')
    if not out_mock or not os.path.isdir(out_mock):
        raise HTTPException(status_code=404, detail='mock not found')
    tmp_zip = os.path.join(tempfile.gettempdir(), f"{run_id}.zip")
    with zipfile.ZipFile(tmp_zip, "w", zipfile.ZIP_DEFLATED) as z:
        for base, prefix in [(out_main, "output"), (out_mock, "mock")]:
            for root, dirs, files in os.walk(base):
                for name in files:
                    full = os.path.join(root, name)
                    rel = os.path.relpath(full, base)
                    arc = os.path.join(prefix, rel)
                    z.write(full, arc)
    def iterfile():
        with open(tmp_zip, "rb") as f:
            while True:
                chunk = f.read(8192)
                if not chunk:
                    break
                yield chunk
    headers = {"Content-Disposition": f"attachment; filename=results-{run_id}.zip"}
    return StreamingResponse(iterfile(), media_type='application/zip', headers=headers)

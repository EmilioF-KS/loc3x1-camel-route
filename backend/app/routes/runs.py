from fastapi import APIRouter, HTTPException
from pydantic import BaseModel
from uuid import uuid4
from ..services.orchestrator import start_run
from fastapi.responses import StreamingResponse
import os
import shutil

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
    # Zip generated/result for the demo; in a full system, track per-run out_path
    src = os.path.join(os.getcwd(), 'generated', 'result')
    if not os.path.isdir(src):
        raise HTTPException(status_code=404, detail='result not found')
    tmp_zip = shutil.make_archive(f'/tmp/{run_id}', 'zip', src)
    def iterfile():
        with open(tmp_zip, 'rb') as f:
            yield from f
    headers = {"Content-Disposition": f"attachment; filename=results-{run_id}.zip"}
    return StreamingResponse(iterfile(), media_type='application/zip', headers=headers)

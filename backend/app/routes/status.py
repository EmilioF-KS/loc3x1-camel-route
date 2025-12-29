from fastapi import APIRouter, HTTPException
from ..services.orchestrator import get_status

router = APIRouter()

@router.get('/runs/{run_id}')
def get_run(run_id: str):
    s = get_status(run_id)
    if not s:
        raise HTTPException(status_code=404, detail='run not found')
    return s

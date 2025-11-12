from fastapi import APIRouter, HTTPException

from ..services.orchestrator import get_run_status


router = APIRouter()


@router.get("/{run_id}")
def get_status(run_id: str):
    status = get_run_status(run_id)
    if status is None:
        raise HTTPException(status_code=404, detail="Run not found")
    return status
import os
import uuid
from fastapi import APIRouter, HTTPException
from pydantic import BaseModel, Field

from ..services.orchestrator import start_run


class RunRequest(BaseModel):
    inputPath: str = Field(..., description="Path to the input folder")
    options: dict | None = Field(default=None, description="Optional flags like dry-run, verbose")


class RunResponse(BaseModel):
    id: str
    status: str


router = APIRouter()


@router.post("", response_model=RunResponse)
def create_run(payload: RunRequest):
    input_path = payload.inputPath

    if not os.path.isdir(input_path):
        raise HTTPException(status_code=400, detail="inputPath must be an existing directory")
    if not os.access(input_path, os.R_OK):
        raise HTTPException(status_code=400, detail="inputPath is not readable")

    run_id = str(uuid.uuid4())
    start_run(run_id=run_id, input_path=input_path, options=payload.options or {})
    return RunResponse(id=run_id, status="queued")
import asyncio
from fastapi import APIRouter, HTTPException
from starlette.responses import StreamingResponse

from ..services.orchestrator import get_run_status


router = APIRouter()


async def sse_event_generator(run_id: str):
    # Simple demo stream: emit status every 500ms; replace with real stage updates
    while True:
        status = get_run_status(run_id)
        if status is None:
            yield f"event: error\ndata: Run {run_id} not found\n\n"
            break
        # SSE format: 'data: <json>' per event
        yield f"event: status\ndata: {status}\n\n"
        if status.get("status") in {"completed", "failed"}:
            break
        await asyncio.sleep(0.5)


@router.get("/{run_id}/stream")
async def stream_status(run_id: str):
    if get_run_status(run_id) is None:
        raise HTTPException(status_code=404, detail="Run not found")
    return StreamingResponse(sse_event_generator(run_id), media_type="text/event-stream")
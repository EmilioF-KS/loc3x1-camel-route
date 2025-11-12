from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from .routes.runs import router as runs_router
from .routes.status import router as status_router
from .routes.stream import router as stream_router


def create_app() -> FastAPI:
    app = FastAPI(title="LOC3X1 Agent API", version="0.1.0")

    app.add_middleware(
        CORSMiddleware,
        allow_origins=["*"],
        allow_credentials=True,
        allow_methods=["*"],
        allow_headers=["*"],
    )

    app.include_router(runs_router, prefix="/runs")
    app.include_router(status_router, prefix="/runs")
    app.include_router(stream_router, prefix="/runs")

    @app.get("/health")
    def health():
        return {"status": "ok"}

    return app


app = create_app()
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from .routes.runs import router as runs_router
from .routes.status import router as status_router
from .routes.inputs import router as inputs_router
from .routes.upload import router as upload_router

app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"]
)
app.include_router(runs_router)
app.include_router(status_router)
app.include_router(inputs_router)
app.include_router(upload_router)

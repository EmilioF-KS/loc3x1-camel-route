import os
import uvicorn


def main():
    host = os.environ.get("HOST", "0.0.0.0")
    port = int(os.environ.get("PORT", "8000"))
    reload = os.environ.get("RELOAD", "true").lower() == "true"

    # Runs the FastAPI app defined in backend/app/main.py
    uvicorn.run("backend.app.main:app", host=host, port=port, reload=reload)


if __name__ == "__main__":
    main()
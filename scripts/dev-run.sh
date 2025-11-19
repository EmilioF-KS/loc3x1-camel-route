#!/usr/bin/env bash
. .venv/bin/activate
uvicorn backend.app.main:app --port 8000 --reload


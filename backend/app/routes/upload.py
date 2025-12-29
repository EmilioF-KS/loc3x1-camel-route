from fastapi import APIRouter, UploadFile, File
from typing import List
import os
import uuid

router = APIRouter()

@router.post('/upload')
async def upload_directory(files: List[UploadFile] = File(...)):
    run_id = str(uuid.uuid4())
    base = os.path.join('/tmp/inputs', run_id)
    os.makedirs(base, exist_ok=True)
    count = 0
    for f in files:
        # Use webkitRelativePath if present
        rel = getattr(f, 'filename', None) or f.filename
        rel = rel.replace('..', '')
        dest = os.path.join(base, rel)
        os.makedirs(os.path.dirname(dest), exist_ok=True)
        content = await f.read()
        with open(dest, 'wb') as fh:
            fh.write(content)
        count += 1
    return {"id": run_id, "path": base, "files": count}

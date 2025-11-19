from fastapi import APIRouter
import os

router = APIRouter()

@router.get('/inputs')
def list_inputs():
    root = os.getcwd()
    chubb = os.path.join(root, 'CHUBB')
    out = []
    for name in ['Dependencies', 'Dependencies2']:
        p = os.path.join(chubb, name)
        if os.path.isdir(p):
            out.append({"name": name, "path": p})
    return {"inputs": out}


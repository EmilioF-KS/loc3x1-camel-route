const base = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8000'

async function createRun(inputPath: string) {
  const r = await fetch(`${base}/runs`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ inputPath })
  })
  return r.json()
}

async function getStatus(id: string) {
  const r = await fetch(`${base}/runs/${id}`)
  return r.json()
}

async function getInputs() {
  const r = await fetch(`${base}/inputs`)
  return r.json()
}

export const api = { createRun, getStatus, getInputs }

async function uploadDirectory(files: FileList) {
  const form = new FormData()
  Array.from(files).forEach(f => form.append('files', f))
  const r = await fetch(`${base}/upload`, { method: 'POST', body: form })
  return r.json()
}

export const uploads = { uploadDirectory }

async function uploadFormData(form: FormData) {
  const r = await fetch(`${base}/upload`, { method: 'POST', body: form })
  return r.json()
}

uploads.uploadFormData = uploadFormData

import React, { useRef, useState } from 'react'
import { uploads } from '../services/api'

type Props = { value: string; onChange: (v: string) => void }

export function InputPathSelector({ value, onChange }: Props) {
  const fileInput = useRef<HTMLInputElement | null>(null)
  const [summary, setSummary] = useState<string>('')
  return (
    <div style={{ marginBottom: 12 }}>
      <label htmlFor="inputPath" style={{ display: 'block', marginBottom: 4 }}>Input folder path</label>
      <div style={{ display: 'flex', gap: 8 }}>
        <input
          id="inputPath"
          value={value}
          onChange={e => onChange(e.target.value)}
          placeholder="/Users/you/CHUBB/Dependencies"
          style={{ flex: 1, padding: 8, border: '1px solid #ccc', borderRadius: 4 }}
        />
        <input ref={fileInput} type="file" multiple style={{ display: 'none' }} onChange={async e => {
          const files = e.target.files
          if (!files || files.length === 0) return
          const res = await uploads.uploadDirectory(files)
          onChange(res.path)
          setSummary(`Uploaded ${res.files} files to ${res.path}`)
        }} />
        <button onClick={async () => {
          const anyWindow: any = window
          if (anyWindow && anyWindow.showDirectoryPicker) {
            try {
              const dirHandle = await anyWindow.showDirectoryPicker()
              const form = new FormData()
              async function walk(handle: any, base: string) {
                for await (const [name, h] of handle.entries()) {
                  const rel = base ? base + '/' + name : name
                  if (h.kind === 'file') {
                    const f = await h.getFile()
                    const nf = new File([f], rel, { type: f.type })
                    form.append('files', nf)
                  } else if (h.kind === 'directory') {
                    await walk(h, rel)
                  }
                }
              }
              await walk(dirHandle, '')
              const res = await uploads.uploadFormData(form)
              onChange(res.path)
              setSummary(`Uploaded ${res.files} files to ${res.path}`)
            } catch {
              if (fileInput.current) {
                fileInput.current.setAttribute('webkitdirectory', '')
                fileInput.current.setAttribute('directory', '')
                fileInput.current.click()
              }
            }
          } else {
            if (fileInput.current) {
              fileInput.current.setAttribute('webkitdirectory', '')
              fileInput.current.setAttribute('directory', '')
              fileInput.current.click()
            }
          }
        }} style={{ padding: 8, borderRadius: 4 }}>Browse…</button>
      </div>
      {summary && <div style={{ marginTop: 6, color: '#555' }}>{summary}</div>}
    </div>
  )
}

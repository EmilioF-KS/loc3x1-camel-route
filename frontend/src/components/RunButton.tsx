import React, { useState } from 'react'
import { api } from '../services/api'

type Props = { inputPath: string; onStarted: (id: string) => void; disabled?: boolean }

export function RunButton({ inputPath, onStarted, disabled }: Props) {
  const [busy, setBusy] = useState(false)
  const start = async () => {
    if (busy || disabled) return
    setBusy(true)
    const r = await api.createRun(inputPath)
    setBusy(false)
    if (r && r.id) onStarted(r.id)
  }
  return (
    <button onClick={start} disabled={busy || disabled} style={{ padding: 8, borderRadius: 4 }}>
      {busy ? 'Starting…' : 'Run Pipeline'}
    </button>
  )
}


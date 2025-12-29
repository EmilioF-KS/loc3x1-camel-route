import React from 'react'

type Props = { runId: string | null; status: any }

export function ProgressPanel({ runId, status }: Props) {
  if (!runId) return null
  const stage = status?.stage || 'queued'
  const step = status?.step ?? (stage === 'queued' ? 0 : stage === 'done' ? status?.total ?? 1 : 1)
  const total = status?.total ?? 1
  const percent = status?.percent ?? Math.round((step / total) * 100)
  const label = `Stage: ${stage} (${step} of ${total} processes)`
  return (
    <div style={{ marginTop: 12 }}>
      <div>Run: {runId}</div>
      <div>{label}</div>
      <div style={{ height: 8, background: '#eee', borderRadius: 4 }}>
        <div style={{ width: `${percent}%`, height: '100%', background: '#4a90e2', borderRadius: 4 }} />
      </div>
    </div>
  )
}

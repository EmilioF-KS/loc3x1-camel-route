import React, { useEffect, useState } from 'react'
import { InputPathSelector } from './components/InputPathSelector'
import { RunButton } from './components/RunButton'
import { ProgressPanel } from './components/ProgressPanel'
import { ResultsPanel } from './components/ResultsPanel'
import { api } from './services/api'

export default function App() {
  const [path, setPath] = useState('')
  const [runId, setRunId] = useState<string | null>(null)
  const [status, setStatus] = useState<any>(null)
  const [results, setResults] = useState<any>(null)

  useEffect(() => {
    if (!runId) return
    let active = true
    const poll = async () => {
      const s = await api.getStatus(runId)
      if (!active) return
      setStatus(s)
      if (s && s.status === 'done') {
        setResults(s.results || null)
      } else {
        setTimeout(poll, 1000)
      }
    }
    poll()
    return () => {
      active = false
    }
  }, [runId])

  return (
    <div style={{ fontFamily: 'system-ui', minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
      <header style={{ padding: 16, borderBottom: '1px solid #ddd' }}>LOC3x Agentic Pipeline</header>
      <main style={{ flex: 1, padding: 16 }}>
        <InputPathSelector value={path} onChange={setPath} />
        <RunButton inputPath={path} onStarted={setRunId} disabled={!path} />
        <ProgressPanel runId={runId} status={status} />
        <ResultsPanel results={results} />
      </main>
      <footer style={{ padding: 16, borderTop: '1px solid #ddd' }}>© LOC3x</footer>
    </div>
  )
}


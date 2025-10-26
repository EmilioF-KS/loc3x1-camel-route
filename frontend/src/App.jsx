import { useEffect, useState } from 'react'
import './App.css'

const apiBase = import.meta.env.VITE_API_BASE_URL || '' // use proxy in dev

function App() {
  const [summary, setSummary] = useState('')
  const [logs, setLogs] = useState([])
  const [selectedLog, setSelectedLog] = useState(null)
  const [logTail, setLogTail] = useState('')
  const [error, setError] = useState('')

  // Run-related state
  const [bundleFile, setBundleFile] = useState(null)
  const [runId, setRunId] = useState('')
  const [runProgress, setRunProgress] = useState(0)
  const [runState, setRunState] = useState('idle')
  const [runMetrics, setRunMetrics] = useState({})
  const [runSummary, setRunSummary] = useState('')
  const [runLogs, setRunLogs] = useState([])
  const [runSteps, setRunSteps] = useState([])

  useEffect(() => {
    // Fetch global summary (last run)
    fetch(`${apiBase}/api/summary`)
      .then(async (r) => {
        if (!r.ok) throw new Error(`Summary ${r.status}`)
        const data = await r.json()
        setSummary(data.content || '')
      })
      .catch((e) => setError(String(e)))
    // Fetch global logs list
    fetch(`${apiBase}/api/logs`)
      .then(async (r) => {
        if (!r.ok) throw new Error(`Logs ${r.status}`)
        const data = await r.json()
        setLogs(Array.isArray(data) ? data : [])
      })
      .catch((e) => setError(String(e)))
  }, [])

  const viewLog = (name) => {
    setSelectedLog(name)
    fetch(`${apiBase}/api/logs/${encodeURIComponent(name)}`)
      .then(async (r) => {
        if (!r.ok) throw new Error(`Log ${name} ${r.status}`)
        const data = await r.json()
        setLogTail(data.tail || '')
      })
      .catch((e) => setError(String(e)))
  }

  const startRun = async () => {
    setError('')
    setRunId('')
    setRunProgress(0)
    setRunState('starting')
    setRunMetrics({})
    setRunSummary('')
    setRunLogs([])

    try {
      const form = new FormData()
      if (bundleFile) form.append('bundle', bundleFile)
      const res = await fetch(`${apiBase}/api/run`, { method: 'POST', body: form })
      if (!res.ok) throw new Error(`Start run failed ${res.status}`)
      const data = await res.json()
      const id = data.id
      setRunId(id)
      setRunState('queued')
      // Begin polling
      pollStatus(id)
    } catch (e) {
      setRunState('idle')
      setError(String(e))
    }
  }

  const pollStatus = async (id) => {
    const poll = async () => {
      try {
        const res = await fetch(`${apiBase}/api/run/${id}/status`)
        if (!res.ok) throw new Error(`Status ${res.status}`)
        const data = await res.json()
        setRunProgress(Number(data.progress || 0))
        setRunState(String(data.state || 'unknown'))
        setRunMetrics(data.metrics || {})
        setRunSteps(Array.isArray(data.steps) ? data.steps : [])
        if (data.state && (data.state === 'completed' || data.state === 'completed_with_errors' || data.state === 'failed')) {
          // Fetch per-run results
          const sRes = await fetch(`${apiBase}/api/run/${id}/results/summary`)
          if (sRes.ok) {
            const sData = await sRes.json()
            setRunSummary(sData.content || '')
          }
          const lRes = await fetch(`${apiBase}/api/run/${id}/results/logs`)
          if (lRes.ok) {
            const lData = await lRes.json()
            setRunLogs(Array.isArray(lData) ? lData : [])
          }
          return // stop polling
        }
      } catch (e) {
        setError(String(e))
      }
      setTimeout(poll, 1000)
    }
    poll()
  }

  const viewRunLog = async (name) => {
    try {
      const res = await fetch(`${apiBase}/api/run/${runId}/results/logs/${encodeURIComponent(name)}`)
      if (!res.ok) throw new Error(`Run log ${name} ${res.status}`)
      const data = await res.json()
      setSelectedLog(name)
      setLogTail(data.tail || '')
    } catch (e) {
      setError(String(e))
    }
  }

  return (
    <div className="container">
      <h1>Evidence Viewer</h1>
      {error && <div className="error">{error}</div>}

      <div className="grid">
        <div className="panel">
          <h2>Run Pipeline</h2>
          <p>Upload a ZIP containing BPEL and dependencies to start a run.</p>
          <input type="file" accept=".zip" onChange={(e) => setBundleFile(e.target.files?.[0] || null)} />
          <button onClick={startRun} disabled={runState === 'starting' || runState === 'queued' || runState === 'running'}>
            {runState === 'running' ? 'Running…' : 'Start'}
          </button>
          <div className="progress">
            <div className="bar" style={{ width: `${runProgress}%` }} />
          </div>
          <p>Progress: {runProgress}%</p>
          <p>Status: {runState} {runMetrics && (runMetrics.reports_scanned || runMetrics.failing_suites) ? ` · Reports: ${runMetrics.reports_scanned} · Failing: ${runMetrics.failing_suites}` : ''}</p>
          {runSteps && runSteps.length > 0 && (
            <>
              <h3>Steps</h3>
              <ul className="steps">
                {runSteps.map((s) => (
                  <li key={s.id} className={`step ${s.state}`}>
                    <span className="indicator">{s.state === 'completed' ? '✓' : s.state === 'failed' ? '✕' : s.state === 'running' ? '⏳' : '•'}</span>
                    <span className="name">{s.name}</span>
                  </li>
                ))}
              </ul>
            </>
          )}
          {runSummary && (
            <>
              <h3>Run Summary</h3>
              <pre className="summary">{runSummary}</pre>
            </>
          )}
          {runLogs.length > 0 && (
            <>
              <h3>Run Logs</h3>
              <ul>
                {runLogs.map((name) => (
                  <li key={name}>
                    <button onClick={() => viewRunLog(name)}>{name}</button>
                  </li>
                ))}
              </ul>
            </>
          )}
        </div>

        <div className="panel">
          <h2>Summary (Latest)</h2>
          {summary ? (
            <pre className="summary">{summary}</pre>
          ) : (
            <p>No summary found. Run tests to generate it.</p>
          )}
        </div>
        <div className="panel">
          <h2>Logs (Latest)</h2>
          {logs.length === 0 && <p>No logs found.</p>}
          <ul>
            {logs.map((name) => (
              <li key={name}>
                <button onClick={() => viewLog(name)}>{name}</button>
              </li>
            ))}
          </ul>
          {selectedLog && (
            <>
              <h3>Tail: {selectedLog}</h3>
              <pre className="log">{logTail}</pre>
            </>
          )}
        </div>
      </div>
    </div>
  )
}

export default App

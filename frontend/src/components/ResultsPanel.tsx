import React from 'react'

type Props = { results: any }

export function ResultsPanel({ results }: Props) {
  if (!results) return null
  return (
    <div style={{ marginTop: 12 }}>
      <div>Results</div>
      <pre style={{ background: '#f8f8f8', padding: 8, borderRadius: 4 }}>{JSON.stringify(results, null, 2)}</pre>
      {results.output && (
        <a href={`${import.meta.env.VITE_API_BASE_URL || 'http://localhost:8000'}/archive/${results.run_id || ''}`}>
          <button style={{ padding: 8, borderRadius: 4 }}>Download Results</button>
        </a>
      )}
    </div>
  )
}

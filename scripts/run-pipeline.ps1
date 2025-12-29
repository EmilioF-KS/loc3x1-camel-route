Param(
  [int]$Number = -1,
  [switch]$VerboseLog
)
# Requirements:
# - Java 17+ and Maven 3.9+ available on PATH
# - Powershell 5+ on Windows
# Behavior:
# - Detects latest resultN and mock-services-resultN (or uses -Number)
# - Builds both with Maven
# - Runs brief health checks on result (8087) and mock (8091)
# - Logs outputs to scripts/logs/ and returns non-zero exit codes on failures

function Write-Info($msg) { Write-Host "[INFO] $msg" -ForegroundColor Cyan }
function Write-Warn($msg) { Write-Host "[WARN] $msg" -ForegroundColor Yellow }
function Write-Err($msg) { Write-Host "[ERROR] $msg" -ForegroundColor Red }

function Require-Cmd($name) {
  if (-not (Get-Command $name -ErrorAction SilentlyContinue)) {
    Write-Err "Required command '$name' not found"
    exit 100
  }
}

function Get-LatestNumber() {
  $base = Join-Path (Get-Location) "generated"
  if (-not (Test-Path $base)) { return -1 }
  $nums = @()
  Get-ChildItem -Path $base -Directory | ForEach-Object {
    if ($_.Name -match '^result(\d+)$') { $nums += [int]$Matches[1] }
    elseif ($_.Name -match '^mock-services-result(\d+)$') { $nums += [int]$Matches[1] }
  }
  if ($nums.Count -eq 0) { return -1 }
  return ($nums | Measure-Object -Maximum).Maximum
}

function Ensure-Dirs($n) {
  $resultDir = "generated/result$n"
  $mockDir = "generated/mock-services-result$n"
  if (-not (Test-Path $resultDir)) { Write-Err "Missing $resultDir"; exit 101 }
  if (-not (Test-Path $mockDir))   { Write-Err "Missing $mockDir"; exit 102 }
  return @($resultDir, $mockDir)
}

function Build-Project($dir) {
  Write-Info "Building $dir"
  $proc = Start-Process -FilePath "mvn" -ArgumentList "-DskipTests","package" -WorkingDirectory $dir -NoNewWindow -PassThru -Wait
  if ($proc.ExitCode -ne 0) { Write-Err "Build failed for $dir with code $($proc.ExitCode)"; exit 200 }
}

function Find-Jar($dir, $pattern="*.jar") {
  $cand = Get-ChildItem -Path (Join-Path $dir "target") -Filter $pattern | Where-Object { $_.Name -notmatch '\.original$' }
  if ($null -eq $cand -or $cand.Count -eq 0) { return $null }
  return $cand[0].FullName
}

function Wait-Ready($url, [int]$retries=30, [int]$delayMs=500) {
  for ($i=0; $i -lt $retries; $i++) {
    try {
      $resp = Invoke-WebRequest -Uri $url -UseBasicParsing -TimeoutSec 5
      if ($resp.StatusCode -ge 200 -and $resp.StatusCode -lt 500) { return $true }
    } catch { Start-Sleep -Milliseconds $delayMs }
  }
  return $false
}

function Test-Run($jarPath, $args, $port, $logPath) {
  Write-Info "Starting test run on port $port for $jarPath"
  $argList = @("-jar", $jarPath) + $args
  $errPath = $logPath -replace '\.log$', '.err.log'
  $proc = Start-Process -FilePath "java" -ArgumentList $argList -NoNewWindow -PassThru -RedirectStandardOutput $logPath -RedirectStandardError $errPath
  Start-Sleep -Milliseconds 1500
  $ok = Wait-Ready "http://localhost:$port/actuator/health"
  if (-not $ok) {
    Write-Err "Health check failed on http://localhost:$port/actuator/health"
    try { $proc | Stop-Process -Force } catch {}
    exit 300
  }
  Write-Info "Health OK on port $port"
  try { $proc | Stop-Process -Force } catch {}
}

# Prereqs
Require-Cmd "java"
Require-Cmd "mvn"

$n = $Number
if ($n -lt 0) { $n = Get-LatestNumber }
if ($n -lt 0) { Write-Err "No generated resultN found"; exit 1 }
$dirs = Ensure-Dirs $n
$resultDir = $dirs[0]; $mockDir = $dirs[1]

# Build
Build-Project $resultDir
Build-Project $mockDir

# Locate jars
$resultJar = Find-Jar $resultDir
$mockJar = Find-Jar $mockDir
if ($null -eq $resultJar) { Write-Err "Result jar not found in $resultDir/target"; exit 201 }
if ($null -eq $mockJar)   { Write-Err "Mock jar not found in $mockDir/target"; exit 202 }

# Logs
$logsDir = Join-Path "scripts" "logs"
New-Item -ItemType Directory -Force -Path $logsDir | Out-Null
$resultLog = Join-Path $logsDir "result$n.log"
$mockLog = Join-Path $logsDir "mock$n.log"

# Test runs (brief health checks)
Test-Run $resultJar @("--server.port=8087") 8087 $resultLog
Test-Run $mockJar @("--server.port=8091") 8091 $mockLog

Write-Info "Pipeline OK for result$n and mock-services-result$n"
exit 0

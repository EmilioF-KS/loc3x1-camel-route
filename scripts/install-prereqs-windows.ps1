Param()
$ErrorActionPreference = "Stop"
function Has-Command($name){ $null -ne (Get-Command $name -ErrorAction SilentlyContinue) }
if (Has-Command "winget") {
  winget install --id EclipseAdoptium.Temurin.17.JDK -e --silent
  winget install --id Apache.Maven -e --silent
  winget install --id OpenJS.NodeJS.LTS -e --silent
} elseif (Has-Command "choco") {
  choco install temurin17 -y
  choco install maven -y
  choco install nodejs-lts -y
} else {
  Write-Host "Please install winget or chocolatey, then re-run this script."; exit 1
}
Write-Host "Prerequisites installed. Run: python run.py"


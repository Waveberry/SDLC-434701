$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$jdkCandidates = @()
if ($env:JAVA_HOME) { $jdkCandidates += $env:JAVA_HOME }
$jdkCandidates += @(Get-ChildItem -Path 'C:/Program Files/Java/jdk-*','C:/Program Files/Eclipse Adoptium/jdk-*' -Directory -ErrorAction SilentlyContinue | Select-Object -ExpandProperty FullName)
$jdk = $jdkCandidates | Where-Object {
    (Test-Path -LiteralPath (Join-Path $_ 'bin/javac.exe')) -and
    (Test-Path -LiteralPath (Join-Path $_ 'bin/jar.exe'))
} | Select-Object -First 1
if (-not $jdk) { throw 'JDK 17 or newer is required. Set JAVA_HOME.' }

$classes = Join-Path $projectRoot 'build/classes'
$jar = Join-Path $projectRoot 'build/WeightConverter.jar'
New-Item -ItemType Directory -Force -Path $classes | Out-Null
$sources = Get-ChildItem -LiteralPath (Join-Path $projectRoot 'src/lab') -Filter '*.java' | Select-Object -ExpandProperty FullName
& (Join-Path $jdk 'bin/javac.exe') -encoding UTF-8 --release 17 -d $classes $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
& (Join-Path $jdk 'bin/jar.exe') --create --file $jar --main-class lab.Main -C $classes .
if ($LASTEXITCODE -ne 0) { throw 'JAR packaging failed.' }
Write-Host "Built $jar"

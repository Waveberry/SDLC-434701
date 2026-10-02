$ErrorActionPreference = 'Stop'
Set-Location -LiteralPath $PSScriptRoot
$jdkCandidates = @()
if ($env:JAVA_HOME) { $jdkCandidates += $env:JAVA_HOME }
$jdkCandidates += Get-ChildItem "$env:USERPROFILE/.vscode/extensions/redhat.java-*/jre/*" -Directory -ErrorAction SilentlyContinue | Select-Object -ExpandProperty FullName
$jdkCandidates += Get-ChildItem 'C:/Program Files/Java/*','C:/Program Files/Eclipse Adoptium/*' -Directory -ErrorAction SilentlyContinue | Select-Object -ExpandProperty FullName
$jdk = $jdkCandidates | Where-Object { Test-Path -LiteralPath (Join-Path $_ 'bin/javac.exe') } | Select-Object -First 1
if (-not $jdk) { throw 'JDK 17 or later is required. Set JAVA_HOME.' }
$output = Join-Path $PSScriptRoot 'output'
$classes = Join-Path $output 'classes'
$jar = Join-Path $output 'WeightConverter.jar'
New-Item -ItemType Directory -Force $classes | Out-Null
$sources = Get-ChildItem src/lab/*.java,tests/lab/*.java | Select-Object -ExpandProperty FullName
& "$jdk/bin/javac.exe" -encoding UTF-8 -g --release 17 -d $classes $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
& "$jdk/bin/java.exe" -cp $classes lab.ModelTest
if ($LASTEXITCODE -ne 0) { throw 'Model tests failed.' }
& "$jdk/bin/jar.exe" --create --file $jar --main-class lab.Main -C $classes lab/WeightUnit.class -C $classes lab/ConversionModel.class -C $classes 'lab/ConversionModel$Result.class' -C $classes lab/MainView.class -C $classes 'lab/MainView$Input.class' -C $classes lab/InputDialog.class -C $classes lab/ConversionController.class -C $classes lab/Main.class
if ($LASTEXITCODE -ne 0) { throw 'Packaging failed.' }
Write-Host "Built $jar"

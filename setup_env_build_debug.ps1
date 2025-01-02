.\load_env.ps1
$buildPath = Join-Path (Get-Location) "target"

if (Test-Path $buildPath) {
    # If the directory exists, do something here
    Write-Host "Build Directory exists"
    Write-Host "Cleaning Maven Build"
    .\mvnw clean
} else {
    # If the directory does not exist, do something else here
    Write-Host "Build Directory does not exist"
}

Write-Host "Packaging Maven Project as JAR"
.\mvnw package
Write-Host "Launching JAR in DEBUG MODE"

java -agentlib:jdwp=transport=dt_shmem,server=y,suspend=n,address=1337 -jar .\target\dream-shops-0.0.1-SNAPSHOT.jar
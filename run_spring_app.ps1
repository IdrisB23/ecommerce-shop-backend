if ($args.Count -eq 0) {
    Write-Host "No CLI arguments passed. Running Spring App"
    .\setup_env_build_run.ps1
    exit
}

Write-Host "Running Spring App in debug mode."
.\setup_env_build_debug.ps1
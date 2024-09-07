# this is a Power-Shell (PS) Script, assuming your are running this app on a windows machine

# Path to YAML file (This is where your secrets/values of environment_variables are stored)
$yamlFilePath = "secrets.yml"

$rawYaml = Get-Content $yamlFilePath -Raw
# Convert YAML to PowerShell Object
$envVars = (ConvertFrom-Yaml -Yaml $rawYaml)


# Set environment variables from YAML
[System.Environment]::SetEnvironmentVariable('MYSQL_DB_HOST', $envVars.environment.MYSQL_DB_HOST)
[System.Environment]::SetEnvironmentVariable('MYSQL_DB_PORT', $envVars.environment.MYSQL_DB_PORT)
[System.Environment]::SetEnvironmentVariable('MYSQL_DB_USERNAME', $envVars.environment.MYSQL_DB_USERNAME)
[System.Environment]::SetEnvironmentVariable('MYSQL_DB_PASSWORD', $envVars.environment.MYSQL_DB_PASSWORD)

# Output to verify environment variables are set
Write-Output "Environment variables loaded:"
Write-Output "MYSQL_DB_HOST=$($envVars.environment.MYSQL_DB_HOST)"
Write-Output "MYSQL_DB_PORT=$($envVars.environment.MYSQL_DB_PORT)"
Write-Output "MYSQL_DB_USERNAME=$($envVars.environment.MYSQL_DB_USERNAME)"
Write-Output "MYSQL_DB_PASSWORD=$($envVars.environment.MYSQL_DB_PASSWORD)"

# Run the Spring Boot application
#Write-Output "Starting Spring Boot application..."
#Start-Process "java" -ArgumentList "-jar your-spring-boot-app.jar"
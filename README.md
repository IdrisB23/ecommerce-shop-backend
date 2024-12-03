# TL;DR

1. Setup
2. Run the App
3. API Testing

## Setup

### Clone the repository

`git clone <repository_url>`

### Setup your database credentials

Currently, the Spring App is run via Powershell scripts (We have a Windows machine), the file `dream-shops/load_env.ps1` reads the secret credentials from a YAML file which should be located in `dream-shops/secrets.yml` and has the following structure:

    environment:
	    PORT: <spring_app_port>
	    MYSQL_DB_HOST: <db_host>
	    MYSQL_DB_PORT: <db_port>
	    MYSQL_DB_USERNAME: <db_username>
	    MYSQL_DB_PASSWORD: <db_password>

Feel free to change the structure of this configuration file (you may use a different Database than MySQL, for instance). However, beware that you need to changed the file `dream-shops/load_env.ps1` accordingly.

## Run the App

There are 4 Powershell scripts under `dream-shops`:
1. `load_env.ps1` reads the secrets/config file and loads environment variables mostly for database connection.
2. `setup_env_build_run.ps1` invokes `load_env.ps1`. It also cleans build artifacts if existing, compiles and builds JAR file, and executes it.
3. `setup_env_build_debug.ps1` invokes `load_env.ps1` does the same as `setup_env_build_run` except it runs the app in debug mode and listens on port 1337 (hard-coded in script) for debugger connection, for instance JDB. It uses shared memory for this connections; You may want to change this by reading the PS script.
4. `run_spring_app.ps1` invokes `setup_env_build_run.ps1` if no CLI argument is passed, otherwise it invokes `setup_env_build_debug.ps1` (exp usage: `.\run_spring_app.ps1 -x`).

## API Testing
For now, only smoke testing is performed on the API endpoints to ensure their desired functionality. We are using Postman as a testing platform.

### Smoke Testing
See `dream-shops/[postman_collection]SpringBootApp.json`. You may load this file onto Postman client to get the collection of requests used for smoke testing:
1. Start the App (see Run the App section).
2. Send Postman requests.
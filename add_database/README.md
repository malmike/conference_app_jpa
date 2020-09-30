# Postgres Course Setup

## Environment setup
Copy sample-database-env to database.env


## Docker Postgres Setup
### Docker Compose
Docker compose contains the instructions for setting up a docker postgress instance.

Copy `sample-database-env` to `database.env` adding your own environment settings.

Start docker using:

    docker-compose up   # you can add -d flag to start docker without logs

Stop docker using:

    docker-compose down

Connect to PSQL prompt from docker:

    docker exec -it $(docker-compose ps -q database) psql -U postgres

Setup the Tables:

    docker cp database/create_tables.sql $(docker-compose ps -q database):/create_tables.sql
    docker exec -it $(docker-compose ps -q database) psql -d conference_app -f create_tables.sql -U postgres

Install the Data:

    docker cp database/insert_data.sql $(docker-compose ps -q database):/insert_data.sql
    docker exec -it $(docker-compose ps -q database) psql -d conference_app -f insert_data.sql -U postgres

Run Bash

    docker-compose run database bash

or

    docker exec -it $(docker-compose ps -q database) bash

### Without the use of docker compose
Create Docker container with Postgres database:

    docker create --name postgres-demo -e POSTGRES_PASSWORD=Welcome -p 5432:5432 postgres:11.5-alpine

Start container:

    docker start postgres-demo

Stop container:

    docker stop postgres-demo

Note: This stores the data inside the container - when you delete the container, the data is deleted as well.

Connect to PSQL prompt from docker:

    docker exec -it postgres-demo psql -U postgres


#### Application Database Setup

Create the Database:

    psql> create database conference_app;

Setup the Tables:

    docker cp database/create_tables.sql postgres-demo:/create_tables.sql
    docker exec -it postgres-demo psql -d conference_app -f create_tables.sql -U postgres

Install the Data:

    docker cp database/insert_data.sql postgres-demo:/insert_data.sql
    docker exec -it postgres-demo psql -d conference_app -f insert_data.sql -U postgres


## JDBC Connection Info:

    JDBC URL: `jdbc:postgresql://localhost:5432/conference_app`

    Username: `postgres`

    Password: `Welcome`


## PSQl commands
Show tables:

    psql conference_app -U postgres -c "\z"

or

    psql conference_app -U postgres -c "\dt"

Show databases:

    psql -U postgres -c "\l"

# Pokedex API
Service to retrieve Pokemon data

## System requirements
- Docker 19.03+
- Docker Compose 1.25+
- Maven 3.6.3+
- OpenJDK 11

## Compilation and Deployment in a local env
This application is dockerized and can be deployed in a local environment following the next steps
1. Install Docker and Docker Compose in your machine.
2. Run the command `mvn clean package` to build the project, this command will generate the file 
`target/pokedex-0.0.1-SNAPSHOT.jar` 
3. In your terminal run the belows command 
    - `docker-compose up -d`
    - if you want to see the logs removed the `-d` parameter from the command
4. You can also run the project without Docker just running the command `java -jar target/pokedex-0.0.1-SNAPSHOT.jar`

## Consuming Endpoints
1. `/pokemon/{page}`
    - This endpoint will allow you to get all pokemons paginated
    - Curl example `curl --location --request GET 'localhost:8080/v1/pokedex/pokemon?page={page-number}'`
    - replace the `{page-number}` with the page you want to consult.

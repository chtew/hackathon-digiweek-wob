# Traffic Analysis for better Mobility and City Planning

Nowaday, city planning should be based on traffic analysis. With our traffic analysis tool you can:
* easily import traffic data via CSV and JSON
* see traffic counts in a time range as city-heat-map
* analyse data via diagrams (grafana integration) and add own diagrams

<img src="https://user-images.githubusercontent.com/10485792/178258676-22aa0ab0-c371-477c-b9a4-1eaaf5af3f6d.png" width="50%"/><img src="https://user-images.githubusercontent.com/10485792/178258651-e6dd5b19-a6b6-4e7d-9594-9ee93bd834d2.png" width="50%"/>

### Prerequisites

* Java JDK 14 or later
* Maven 3
* NodeJs (16.9.1) and NPM (8.3.2) - [NodeJS Install](https://nodejs.org/en/download/package-manager/)
* mariaDB 10.6 (available for development via docker-compose scripts)
* docker, docker compose, [docker compose cli plugin](https://docs.docker.com/compose/install/compose-plugin/)
* using Keycloak is optional

### How-to run (in project root folder)
- Install frontend dependencies\
  `npm install --prefix webclient/app/ --legacy-peer-deps`
- Build project\
  `mvn clean package -P frontend`
- Run Docker-Compose project\
  `docker compose up --build`

### Installation Steps

:exclamation: Each step is executed from the project home directory.

1) go to the deployment folder and start the environment (database and keycloak) via docker-compose:

    ```bash
    cd deployment
    docker-compose -f localenv-docker-compose.yml up
    ```

2) go to `webclient/app` and install the frontend applications dependencies

    ```bash
    cd webclient/app
    npm install --legacy-peer-deps
    ```

3) build the project

    ```bash
    mvn clean install -P frontend
    ```

4) start project

    ```bash
    java -jar application/target/application-0.0.1-SNAPSHOT.jar
    ```
   You can also run the main-class via Visual Studio Code.


* **Project Builder can be reached under http://localhost:8081/digiweek/**
* **If you are using keycloak:**
    * **default user/password is admin/admin**
    * **keycloak can be reached under http://localost:8081/auth**

### Debugging

#### Frontend Debugging

For debugging, you can start the frontend separately.

```shell
cd webclient/app
npm start
```
NPM server starts under localhost:3000/digiweek/ by default

! If you are using the installation with keycloak, make sure you are logged in before first usage - just go to localhost:8081/digiweek in your browser.

#### Backend Debugging

You can start the spring boot application in debug mode. See Spring Boot documentation for further details. The easiest way is, to use debug functionality integrated with your IDE like VS Code.

### MariaDB Client

The database is available under localhost:3006

```
Username:hackathon
Database:hackathon
Password:hackathon
```
MySQLWorkbench is recommended to access database for development purpose.

### Starting without keycloak

If you want to start your application without keycloak, you need to change spring boot profile to dev in application\src\main\resources\application.properties.

```properties
spring.profiles.active=dev
```

or define env-variable

```bash
SPRING_PROFILES_ACTIVE=dev
```

Start the database without keycloak:

```bash
cd deployment
docker-compose -f localenv-docker-compose.yml up
```

### How-to import data with curl
`curl -F "file=@/home/florian/dev/hackathon/Datenlieferung/Geodaten/Zaehlschleifen_Standorte.json" localhost:8081/hackathon/api/trafficrecorder/trafficRecordersJSON`
`curl -F "file=@/home/florian/dev/hackathon/Datenlieferung/Daten/Verkehrszaehlschleifen_Digiweek.csv" localhost:8081/hackathon/api/trafficrecord/inductionLoopCsv`

# Prerequisite info 

```bash
The project was made using these technologies:
 1. Java 11
 2. SpringBoot
 3. Node.js
 4. React.js
 5. Gradle
 6. Docker
 7. NPM
```

# Additional info 

```bash
React app is located under src/main/webapp folder
Login user is already preinserted into the username and password fields for you.
Test user credentials are: user:123123

* When running locally the system is using in memory H2 database for
fast development.
* When running in docker latest Postgres DB is used
```

# How to run in Docker

```bash
Make sure you have docker-compose installed.
Clone the project.

From the root folder execute these commands in terminal:
1. ./gradlew bootJar (for Docker to pickup executable jar)
2. docker-compose up
```

# How to run for local development

```bash
From the root folder execute these commands in terminal:
./gradlew bootRun

From the React app folder src/main/webapp run 
npm run start
```
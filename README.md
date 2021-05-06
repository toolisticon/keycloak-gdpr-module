[![CI](https://github.com/toolisticon/keycloak-gdpr-module/actions/workflows/build.yml/badge.svg)](https://github.com/toolisticon/keycloak-gdpr-module/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=toolisticon_keycloak-gdpr-module&metric=alert_status)](https://sonarcloud.io/dashboard?id=toolisticon_keycloak-gdpr-module)

- [Keycloak GDPR Module](#keycloak-gdpr-module)
  - [Setup](#setup)
  - [Deploy into a standalone keycloak server](#deploy-into-a-standalone-keycloak-server)
  - [Development](#development)
    - [Local Keycloak server](#local-keycloak-server)
# Keycloak GDPR Module

> A module

- [Keycloak GDPR Module](#keycloak-gdpr-module)
  - [Setup](#setup)
  - [Deploy into a standalone keycloak server](#deploy-into-a-standalone-keycloak-server)
  - [Development](#development)
    - [Local Keycloak server](#local-keycloak-server)

## Setup

Prerequisites:
* JDK 11+
* Docker

Start the docker stack:

```
docker-compose up -d
```

Build the EAR:

1. build with maven
   * `mvn clean package -DskipTests`
2. find the gdpr-for-keycloak artifact in:
   `deployment/target/gdpr-module-for-keycloak-${project.incremental.version}.ear`

## Deploy into a standalone keycloak server

Copy the built artifact into the directory `${keycloak.home}/standalone/deployments` of a keycloak server.  
**NOTE:** *If the server is running during deployment you need to restart it!*


## Development
### Local Keycloak server
A local Keycloak server for developing and testing the spi is available as a Docker container.

To bring up the server, make sure you’ve installed and started [Docker Community Edition](https://docs.docker.com/engine/installation/), then use the following commands:
```bash
$ docker-compose up
```
The Keycloak server will now be available on <http://localhost:8888>. You can log into the Administration Console using “**admin**” as both username and password.

To test the module build it
```
$ ./mvn package
```
[![CI](https://github.com/toolisticon/keycloak-gdpr-module/actions/workflows/build.yml/badge.svg)](https://github.com/toolisticon/keycloak-gdpr-module/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.toolisticon.keycloak%3Agdpr-module&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.toolisticon.keycloak%3Agdpr-module)

- [Keycloak GDPR Module](#keycloak-gdpr-module)
  - [Setup](#setup)
  - [Deploy into a standalone keycloak server](#deploy-into-a-standalone-keycloak-server)
# Keycloak GDPR Module

> A module

- [Keycloak GDPR Module](#keycloak-gdpr-module)
  - [Setup](#setup)
  - [Deploy into a standalone keycloak server](#deploy-into-a-standalone-keycloak-server)

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
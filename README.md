[![CI](https://github.com/toolisticon/keycloak-gdpr-module/actions/workflows/build.yml/badge.svg)](https://github.com/toolisticon/keycloak-gdpr-module/actions/workflows/build.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=toolisticon_keycloak-gdpr-module&metric=alert_status)](https://sonarcloud.io/dashboard?id=toolisticon_keycloak-gdpr-module)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=toolisticon_keycloak-gdpr-module&metric=coverage)](https://sonarcloud.io/dashboard?id=toolisticon_keycloak-gdpr-module)
[![Known Vulnerabilities](https://snyk.io/test/github/toolisticon/keycloak-gdpr-module/badge.svg)](https://snyk.io/test/github/toolisticon/keycloak-gdpr-module)

- [Keycloak GDPR Module](#keycloak-gdpr-module)
  - [Setup](#setup)
  - [Deploy into a standalone keycloak server](#deploy-into-a-standalone-keycloak-server)
  - [Development](#development)
    - [Local Keycloak server](#local-keycloak-server)
  - [Testing](#testing)
    - [Performance Tests](#performance-tests)

# Keycloak GDPR Module

A Keycloak module to assist storing data in a GDPR compliant way, using Crypto Shredding.
It stores the users keys and encrypt/decrypt the data, so that cryptographic keys will never
leave the system.

The module is currently a Proof-of-Concept and has not yet been tested in production use cases.

## Setup

Prerequisites:
* JDK 11+
* Docker

Build and start:
```bash
# Start keycloak and MySQL database
docker-compose up -d

# Build the GDPR module
mvn clean package -DskipTests

# Copy the Build artifact into the Keycloak container
.bin/update-spi.sh
```

The Keycloak server will now be available on <http://localhost:8888>. You can log into the Administration Console using “**admin**” as both username and password.


## Deploy into a standalone keycloak server

Copy the built artifact from `./deployment/target/gdpr-module-for-keycloak-${project.incremental.version}.ear` into the directory `${keycloak.home}/standalone/deployments` of a keycloak server.  
**NOTE:** *If the server is running during deployment you need to restart it!*

## Testing

### Performance Tests

For performance we're using (Gatling)[https://gatling.io/docs/current/quickstart/]:
```
(cd spi/ && ../mvnw gatling:test)
```

### Debugging

To debug the deployed module:
```bash
$ docker compose up
$ .bin/update-spi.sh
```
then connect via Remote Debugging:
```
-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:9097
```

#!/usr/bin/env bash

docker cp ./deployment/target/gdpr-module-for-keycloak.ear keycloak:/opt/jboss/keycloak/standalone/deployments/gdpr-module-for-keycloak.ear

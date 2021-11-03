#!/usr/bin/env bash

echo "Prepare Env"
./mvnw clean package -DskipTests=true
docker-compose up -d
echo -n 'wait for app to be ready '; until $(curl --output /dev/null --silent --head --fail http://localhost:8888); do printf '.'; sleep 5; done;
.bin/update-spi.sh
echo " Run Gatling tests"
(cd spi/ && ../mvnw gatling:test)
echo "Prepare reports folder"
mkdir -p public/test-reports/gatling
cp -rp spi/target/gatling/* public/test-reports/gatling/

#!/bin/bash
mvn package -Dmaven.test.skip
docker build . -t hayat_service_discovery:latest

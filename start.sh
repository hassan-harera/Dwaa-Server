#!/bin/bash
docker-compose -f docker-compose.yml down

#Maven build
mvn package -Dmaven.test.skip

#Docker build
docker build ./system/service-discovery -t hayat_service_discovery:latest
docker build ./core -t hayat_app:latest

docker-compose -f docker-compose.yml up -d

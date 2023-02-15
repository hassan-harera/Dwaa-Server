#!/bin/bash
docker-compose -f docker-compose.yml down

#Maven build
mvn clean package -Dmaven.test.skip

#Docker build

docker build ./system/service-discovery -t hayat_service_discovery:latest
docker build ./system/config-service -t hayat_config_service:latest
docker build ./system/gateway -t hayat_gateway_service:latest

docker build ./core -t hayat_app:latest
docker build ./services/donation -t hayat_donation_service:latest

docker-compose -f docker-compose.yml up -d

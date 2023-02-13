#!/bin/bash
docker-compose -f docker-compose.yml down

mvn clean package -Dmaven.test.skip
docker build ./core -t hayat_app:latest

docker-compose -f docker-compose.yml up -d

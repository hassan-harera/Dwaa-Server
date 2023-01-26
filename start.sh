#!/bin/bash
docker-compose -f docker-compose.yml down
mvn package -Dspring.test.skip=true
docker build . -t hayat_app:latest
docker-compose -f docker-compose.yml up -d

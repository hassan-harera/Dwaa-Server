#!/bin/bash
docker-compose -f docker-compose.yml down

#Maven build
mvn package -Dmaven.test.skip

#Docker build
bash ./system/service-discovery/build.sh
bash ./core/build.sh

docker-compose -f docker-compose.yml up -d

#!/bin/bash
#Maven build
mvn clean package -Dmaven.test.skip

#Docker build
docker build . -t hayat_app:latest

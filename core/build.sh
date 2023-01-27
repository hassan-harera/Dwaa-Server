#!/bin/bash
mvn package -Dspring.test.skip=true
docker build . -t hayat_app:latest

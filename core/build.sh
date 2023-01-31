#!/bin/bash
mvn package -Dmaven.test.skip
docker build . -t hayat_app:latest

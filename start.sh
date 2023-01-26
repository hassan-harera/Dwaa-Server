#!/bin/bash
docker-compose -f docker-compose.yml down
docker build . -t hayat_app:latest
docker-compose -f docker-compose.yml up -d

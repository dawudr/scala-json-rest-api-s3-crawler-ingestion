#!/bin/bash
# 
echo "Running Ingestion Pipeline: Storelocation REST -> S3"
echo "sbt runMain com.sainsburys.dpp.rest.http.RestClientSession config=application.conf"
sbt "runMain com.sainsburys.dpp.rest.http.RestClientSession config=application.conf"
echo "Finished Ingestion Pipeline: Storelocation REST -> S3"

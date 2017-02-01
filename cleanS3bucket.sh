#!/bin/bash
#
echo "Showing S3 Bucket....."
echo "aws s3 rm s3://js-dpp3-bds-data-store-updates-app-test --recursive --include \"*.json\""
aws s3 rm s3://js-dpp3-bds-data-store-updates-app-test --recursive --include "*.json"
# dpp-bds-rest-connector

## Descriprion
Stand alone Scala application which serialises JSON response from a defined REST API to an Amazon S3 bucket. The application crawls paginated result by incrementing the 'offset' parameter whilst checking that list node in the JSON response has results. Crawl ends either when the list node in the JSON response returns no results or the Max offset value has been reached. When saving content to the datastore the key or filename format is:

```<api.webservice.name>_<offset>_<date time stamp in yyyy-MM-dd-HH-mm format>.json ```

## Prerequisites
1. sbt 0.13.13
2. Java 8

## Download
```
git clone git@github.com:JSainsburyPLC/dpp-bds-rest-connector.git
```

## Build
```bash
cd /path_to_source
sbt package
```

## Run


### SBT
```bash
cd /path_to_source
sbt run config=path_to_config_file arg2 arg3 ...
```
### Jar
```bash
java -jar /path_to_jar/rest-connector_2.10-1.0.jar config=path_to_config_file  arg2 arg3 ...
```

## Config file

To read the configuration parameters https://github.com/typesafehub/config is being used so any format supported by them is implicitily supported. The sample-application.conf file is provided. Ensure you copy this and create application.conf with your credentials filled in and include "config=application.conf" in program arguments when running the application.

| Property name | Description   |
| ------------- |:-------------:|
| s3AccessKey   | Please provide an Amazon S3 access key  |
| s3SecretKey   | Please provide an Amazon S3 secret key |
| s3BucketName   | Amazon S3 bucket name |
| s3KeyPrefix   | Defines the prefix for bucket's keys |
| api.webservice.name   | Rest API Service name |
| api.webservice.url   | Rest API endpoint URL |
| api.webservice.param.api_client_id   | Rest API Application Id |
| api.webservice.param.limit   | Rest API results page limit |
| api.webservice.param.offset   | Rest API page offset|
| api.webservice.param.max.offset   | Maximum page offset to prevent infinite crawling |
| api.webservice.output.path | For use with LocalFileResponseWriter. This is the file output path on local disk |
| api.webservice.list.nodename | The nodename to identify node that contains the list of results that we iterate on i.e results |

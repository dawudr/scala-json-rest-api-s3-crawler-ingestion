package com.sainsburys.dpp.rest.config

object ConfigPropertiesFactory {

  def load (args: Array[String]) : ConfigProperties = {

    def argsToMap(args: Array[String]) : collection.immutable.Map[String, String] = {
      var argsAsMap = collection.mutable.Map[String, String]()
      args.foreach(arg => {
        val argParts = arg.split("=")
        if (argParts.size == 2) {
          argsAsMap += argParts{0} -> argParts{1}
        }
      })
      argsAsMap.toMap
    }

    val argsAsMap = argsToMap(args)

    val configProperties = argsAsMap.contains("config") match {
      case true => ConfigProperties(argsAsMap("config"))
      case false => ConfigProperties()
    }

    def lookForConfigArgs = {
      if (argsAsMap.contains("api.webservice.name"))
        configProperties.apiName = argsAsMap("api.webservice.name")

      if (argsAsMap.contains("api.webservice.url"))
        configProperties.apiUrl = argsAsMap("api.webservice.url")

      if (argsAsMap.contains("api.webservice.param.api_client_id"))
        configProperties.apiApiClientId = argsAsMap("api.webservice.param.api_client_id")

      if (argsAsMap.contains("api.webservice.param.limit"))
        configProperties.apiLimit = argsAsMap("api.webservice.param.limit").toInt

      if (argsAsMap.contains("api.webservice.param.offset"))
        configProperties.apiOffset = argsAsMap("api.webservice.param.offset").toInt

      if (argsAsMap.contains("api.webservice.param.max.offset"))
        configProperties.apiMaxOffset = argsAsMap("api.webservice.param.max.offset").toInt

      if (argsAsMap.contains("api.webservice.output.path"))
        configProperties.apiOutputPath = argsAsMap("api.webservice.output.path")

      if (argsAsMap.contains("api.webservice.list.nodename"))
        configProperties.apiListNodeName = argsAsMap("api.webservice.list.nodename")

      if (argsAsMap.contains("s3AccessKey"))
        configProperties.s3AccessKey = argsAsMap("s3AccessKey")

      if (argsAsMap.contains("s3SecretKey"))
        configProperties.s3SecretKey = argsAsMap("s3SecretKey")

      if (argsAsMap.contains("s3BucketName"))
        configProperties.s3BucketName = argsAsMap("s3BucketName")

      if (argsAsMap.contains("s3KeyPrefix"))
        configProperties.s3BucketName = argsAsMap("s3KeyPrefix")
    }

    lookForConfigArgs

    configProperties
  }
}

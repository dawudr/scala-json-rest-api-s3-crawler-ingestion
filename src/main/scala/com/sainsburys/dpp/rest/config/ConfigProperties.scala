package com.sainsburys.dpp.rest.config

/**
  * Properties for stylesheet location, source and output paths
  *
  * @author Dawud Rahman (dawud.rahman@sainsburys.co.uk)
  * @version 1.0
  * @since 10/01/2017
  */
sealed class ConfigProperties (var apiName:String,
                               var apiUrl:String,
                               var apiApiClientId:String,
                               var apiLimit:Int,
                               var apiOffset:Int,
                               var apiMaxOffset:Int,
                               var apiOutputPath:String,
                               var apiListNodeName:String,
                               var s3AccessKey:String,
                               var s3SecretKey:String,
                               var s3BucketName:String,
                               var s3KeyPrefix:String
                              )


object ConfigProperties {

  def apply(configFilePath:String): ConfigProperties = {
    val configFile = ConfigurationFactory.load(configFilePath)

    new ConfigProperties(
      configFile.getString("api.webservice.name"),
      configFile.getString("api.webservice.url"),
      configFile.getString("api.webservice.param.api_client_id"),
      configFile.getInt("api.webservice.param.limit"),
      configFile.getInt("api.webservice.param.offset"),
      configFile.getInt("api.webservice.param.max.offset"),
      configFile.getString("api.webservice.output.path"),
      configFile.getString("api.webservice.list.nodename"),
      configFile.getString("s3AccessKey"),
      configFile.getString("s3SecretKey"),
      configFile.getString("s3BucketName"),
      configFile.getString("s3KeyPrefix")
    )
  }

  def apply(): ConfigProperties = new ConfigProperties("","","",0,0,0,"","","","","","")
}
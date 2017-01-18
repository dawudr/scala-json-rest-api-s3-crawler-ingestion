package com.sainsburys.dpp.rest

import java.util.Date

import com.sainsburys.dpp.rest.config.ConfigPropertiesFactory
import com.sainsburys.dpp.rest.http.{LocalFileStoreResponseWriter, ResponseWriter, RestClient}
import com.sainsburys.dpp.rest.service.RestClientService

object RestClientSession$Test extends Loggable {

  def main(args: Array[String]): Unit = {
    if (args.length == 0) {
      println("Please supply an application.conf file")
      System.exit(0)
    }
    val tStart = new Date().getTime
    logger.info("RestClientSession started...")
    lazy val configProperties = ConfigPropertiesFactory.load(args)
    lazy val responseWriter: ResponseWriter = LocalFileStoreResponseWriter(configProperties)
    val restClient: RestClient = RestClient(configProperties, responseWriter)

    // create instance of rest client and send request
    val service = new RestClientService(restClient)

    // call crawler service
    service.crawlStoreLocationData(configProperties.apiLimit,
      configProperties.apiOffset,
      configProperties.apiListNodeName)

    logger.info("RestClientSession finished the task successfully. Time taken: {}ms", new Date().getTime - tStart)
    System.exit(0)
  }
}

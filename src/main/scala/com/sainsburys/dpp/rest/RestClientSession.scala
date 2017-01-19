package com.sainsburys.dpp.rest.http
import java.util.Date

import com.sainsburys.dpp.rest.Loggable
import com.sainsburys.dpp.rest.config.ConfigPropertiesFactory
import com.sainsburys.dpp.rest.s3.BdsS3Client
import com.sainsburys.dpp.rest.service.RestClientService

/**
  * Crawl REST Api and download as json files to S3 Bucket
  */
object RestClientSession extends Loggable {

  def main(args: Array[String]): Unit = {

    if (args.length == 0) {
      println("Please supply a application.conf file")
      System.exit(0)
    }
    val tStart = new Date().getTime
    logger.info("RestClientSession started...")
    lazy val configProperties = ConfigPropertiesFactory.load(args)
    lazy val bdsS3Client: BdsS3Client = BdsS3Client(configProperties)
    lazy val responseWriter: ResponseWriter = S3ResponseWriter(bdsS3Client)
    val restClient: RestClient = RestClient(configProperties, responseWriter)

    // create instance of rest client and send request
    val service = new RestClientService(restClient)

    // call crawler service
    service.crawlStoreLocationData(configProperties.apiLimit,
      configProperties.apiOffset,
      configProperties.apiListNodeName)

    logger.info("RestClientSession finished the task successfully. Time taken: {}ms", new Date().getTime - tStart)
  }

}

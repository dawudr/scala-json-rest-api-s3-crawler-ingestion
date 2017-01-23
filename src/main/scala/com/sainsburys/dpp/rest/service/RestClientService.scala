package com.sainsburys.dpp.rest.service

import com.sainsburys.dpp.rest.Loggable
import com.sainsburys.dpp.rest.http.{RestClient, RestClient$}
import com.sainsburys.dpp.rest.s3.BdsS3Client

/**
  * REST Client Service
  */
class RestClientService(private val restClient: RestClient) extends Loggable {

  logger.info("Calling services")

  /**
    * Return response as String. For Testing purposes only.
    */
  def getStoreLocation(limit:Int, offset:Int):String = {
    val response = restClient.sendGetRequest(limit, offset)
    response.body
  }

  /**
    * Save single REST api response and output to datastore
    */
  def getStoreLocation(limit:Int): Unit = {
    restClient.getContent(limit)
  }

  /**
    * Crawl all paginated results for each REST api request response and output to datastore
    */
  def crawlStoreLocationData(limit:Int, offset:Int, listIteratorNodeName:String): Unit = {
    restClient.crawler(limit, offset, listIteratorNodeName)
  }

}

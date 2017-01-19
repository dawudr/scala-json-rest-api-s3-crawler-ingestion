package com.sainsburys.dpp.rest.http

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.sainsburys.dpp.rest.config.ConfigProperties
import org.apache.http.HttpStatus
import org.apache.http.entity.ContentType
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

import scala.util.control.Breaks._
import scalaj.http._

/**
  * HTTP Rest Client Connector
  */
class RestClient(private val apiName: String,
                 private val apiUrl: String,
                 private val apiClientId: String,
                 private val apiMaxOffset: Int,
                 private val responseWriter: ResponseWriter) extends HttpClient {

  logger.info("Initialising instance of API NAME: {}. ", apiName + " API URL:" + apiUrl + ". AppClientID: " + apiClientId)

  /**
    * Returns the HTTP response object from a REST api.
    */
  def sendGetRequest(limit: Int = 50, offset: Int = 0): HttpResponse[String] = {
    val params = Map("api_client_id" -> apiClientId, "limit" -> limit.toString, "offset" -> offset.toString)
    logger.info("Sending GET request to endpoint: {}", apiUrl + params.map(pair => pair._1+"="+pair._2).mkString("?","&",""));
    val httpResponse: HttpResponse[String] = Http(apiUrl)
      .params(params)
      .timeout(connTimeoutMs = 2000, readTimeoutMs = 5000)
      .asString
    if (httpResponse.code != HttpStatus.SC_OK) {
      logger.error("Failed : HTTP response {}", httpResponse)
    } else {
      if (!httpResponse.contentType.get.equals(ContentType.APPLICATION_JSON.getMimeType)) {
        logger.error("Failed: HTTP response content-type required: " + ContentType.APPLICATION_JSON.getMimeType + ". Actual content-type was {} ", httpResponse.contentType.get)
      } else {
        logger.info("Success Response: {} ", httpResponse.headers)
      }
    }
    httpResponse
  }


  /**
    * Crawls REST api where there is an offset, limit parameter and a parent of list nodename to iterate over
    */
  def crawler(limit: Int, offset: Int, listIteratorNodeName: String) = {
    logger.info("Crawler starting at OFFSET: {} ", offset);

    val mapper = new ObjectMapper()
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    val timeStamp = new DateTime();
    val dateFormatGeneration: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm");

    var counter = 0
    var resultsCounter = 0

    // for loop execution with a range
    breakable {
      for (currentOffset <- 50 until apiMaxOffset by offset) {
        // Make Rest API Request
        val response = sendGetRequest(limit.toInt, currentOffset)
        //read JSON like DOM Parser
        val rootNode = mapper.readTree(response.body);
        val resultsNode = rootNode.path(listIteratorNodeName);
        if (resultsNode.isArray) {
          val size = resultsNode.size();
          if (size == 0) {
            logger.info("Crawler ended at OFFSET: {} ", currentOffset + " Total items found: " + (resultsCounter));
            logger.info("Total Response count: {}.", counter + " Time taken: " + (new DateTime().toDate.getTime - timeStamp.toDate.getTime).toString) + "ms";
            break()
          } else {
            val key = apiName + "_offset_" + currentOffset.toString + "_" + dateFormatGeneration.print(timeStamp)
            responseWriter.writeContent(response, key)
            counter += 1
            resultsCounter += size
          }
        }

        //check if we reached maxOffset Limit to prevent crawling forever
        if ((currentOffset + offset) >= apiMaxOffset) {
          logger.info("Max Offset for Crawler reached. Ended at OFFSET: {}. ", currentOffset + " Total items found: " + (resultsCounter));
          logger.info("Total Response count: {}.", counter + " Time taken: " + ((new DateTime().toDate.getTime - timeStamp.toDate.getTime).toString + "ms"));
        }
      }
    }
  }
}

object RestClient {
  def apply(configProperties: ConfigProperties, responseWriter: ResponseWriter): RestClient = {
    new RestClient(configProperties.apiName,
      configProperties.apiUrl,
      configProperties.apiApiClientId,
      configProperties.apiMaxOffset,
      responseWriter)
  }
}


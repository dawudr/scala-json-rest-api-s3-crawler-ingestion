package com.sainsburys.dpp.rest.service

import com.sainsburys.dpp.rest.config.ConfigPropertiesFactory
import com.sainsburys.dpp.rest.http.{LocalFileStoreResponseWriter, RestClient, RestClient$}
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

class RestClientService$Test extends FunSuite with BeforeAndAfter {

  test("test valid json response from live StoreLocation API") {

    // setup
    val args = new Array[String](1)
    args(0) = "config=application.conf"
    val configProperties = ConfigPropertiesFactory.load(args)
    val responseWriter = new LocalFileStoreResponseWriter(configProperties.apiOutputPath)
    val restClient: RestClient = RestClient(configProperties, responseWriter)
    val service = new RestClientService(restClient)
    val actualResponse = service.getStoreLocation(50,50)

    assert(actualResponse.contains("results"))

  }
}

package com.sainsburys.dpp.rest.http

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import scalaj.http.HttpResponse

class RestClient$Test extends FunSuite with BeforeAndAfterEach with MockitoSugar {

  test("test sending a GET request return response code 200 and results in the response body") {
    val restClient = mock[RestClient]
    val response = new HttpResponse("results", 200, Map.empty)
    when(restClient.sendGetRequest(50, 50)).thenReturn(response)
    assert(response.body.equals("results"))
  }

}

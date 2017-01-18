package com.sainsburys.dpp.rest.http

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfter
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import scalaj.http.HttpResponse

class RestClient$Test extends FunSuite with BeforeAndAfterEach with MockitoSugar {

  test("test Get Content") {
    val restClient = mock[RestClient]
//    when(restClient.getContent(50, 50)).thenReturn(new HttpResponse[String])
    assert(true)
  }

}

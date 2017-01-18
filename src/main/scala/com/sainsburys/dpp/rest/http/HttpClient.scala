package com.sainsburys.dpp.rest.http

import com.sainsburys.dpp.rest.Loggable

import scalaj.http.HttpResponse

/**
  * Interface to define supported methods for REST data sources
  *
  * @author Dawud Rahman (dawud.rahman@sainsburys.co.uk)
  * @version 1.0
  * @since 11/01/2017
  */
trait HttpClient extends Loggable {
  def sendGetRequest(limit: Int, offset: Int):  HttpResponse[String]
}

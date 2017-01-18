package com.sainsburys.dpp.rest.http

import com.sainsburys.dpp.rest.Loggable

import scalaj.http.HttpResponse

/**
  * Interface to serialise response to multiple data stores i.e local, hdfs, S3 etc
  */
trait ResponseWriter extends Loggable {
  def writeContent(response: HttpResponse[String], target:String)
}

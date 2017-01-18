package com.sainsburys.dpp.rest.http

import java.io.{BufferedWriter, File, FileWriter}

import com.sainsburys.dpp.rest.config.ConfigProperties
import org.joda.time.DateTime

import scalaj.http.HttpResponse

class LocalFileStoreResponseWriter(private val outputPath: String) extends ResponseWriter {

  override def writeContent(response: HttpResponse[String], key: String): Unit = {
    // Save to disk
    val jsonFileName = outputPath + File.separator + key + ".json"
    logger.info("Writing content to File: {} ", jsonFileName);

    // FileWriter
    val folder = new File(outputPath)
    if(!folder.exists()) {
      folder.mkdir()
    }
    val file = new File(jsonFileName)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(response.body.toString)
    bw.close()
  }
}

object LocalFileStoreResponseWriter {
  def apply(configProperties: ConfigProperties): LocalFileStoreResponseWriter = {
    new LocalFileStoreResponseWriter(configProperties.apiOutputPath)
  }
}

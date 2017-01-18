package com.sainsburys.dpp.rest.http

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import com.amazonaws.services.s3.model.ObjectMetadata
import com.sainsburys.dpp.rest.s3.{BdsS3Client, S3Object}
import org.apache.http.entity.ContentType

import scalaj.http.HttpResponse

/**
  * Serialise REST Response to S3
  */
class S3ResponseWriter(private val s3Client: BdsS3Client) extends ResponseWriter {

  override def writeContent(response: HttpResponse[String], key:String): Unit = {
    // Save to S3
    logger.info("Writing content to S3 Bucket as Key: {} ", key + ".json");
    val fileContentBytes = response.body.toString.getBytes(StandardCharsets.UTF_8);
    val fileInputStream = new ByteArrayInputStream(fileContentBytes);
    val metadata = new ObjectMetadata();
    metadata.setContentType(ContentType.APPLICATION_JSON.toString);
    metadata.setContentLength(fileContentBytes.length);
    val s3Object = new S3Object(key + ".json", fileInputStream, metadata)
    s3Client.sendObjectToS3(s3Object)
  }
}

object S3ResponseWriter {
  def apply(bdsS3Client: BdsS3Client): S3ResponseWriter = {
    new S3ResponseWriter(bdsS3Client)
  }
}
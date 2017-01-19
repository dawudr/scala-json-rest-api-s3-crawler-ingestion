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

  val METADATA_SOURCE_NAME = "SOURCE_NAME"
  val METADATA_SOURCE_ID = "SOURCE_ID"
  val METADATA_HASH_SOURCE_ID = "HASH_SOURCE_ID"
  val METADATA_PROCESS_ID = "PROCESS_ID"
  val METADATA_CREATED_DATETIME = "CREATED_DATETIME"
  val METADATA_CONTENT_LENGTH = "CONTENT_LENGTH"
  val METADATA_CONTENT_TYPE = "CONTENT_TYPE"

  val METADATA_SOURCE_NAME_VALUE = "Storelocation 2.0"
  val METADATA_SOURCE_ID_VALUE = "STORE-SRC-ID-0001"
  val METADATA_PROCESS_ID_VALUE = "STORE-PROC-ID-0001"

  override def writeContent(response: HttpResponse[String], key:String): Unit = {
    // Save to S3
    logger.info("Writing content to S3 Bucket as Key: {} ", key + ".json");
    val fileContentBytes = response.body.toString.getBytes(StandardCharsets.UTF_8);
    val fileInputStream = new ByteArrayInputStream(fileContentBytes);
    val metadata = new ObjectMetadata();
    metadata.setContentType(ContentType.APPLICATION_JSON.toString);
    metadata.setContentLength(fileContentBytes.length);

    // Attach metadata
    val metadataMap = new java.util.HashMap[String, String]()
    metadataMap.put(METADATA_SOURCE_NAME, METADATA_SOURCE_NAME_VALUE)
    metadataMap.put(METADATA_SOURCE_ID, METADATA_SOURCE_ID_VALUE)
    metadataMap.put(METADATA_HASH_SOURCE_ID, key)
    metadataMap.put(METADATA_PROCESS_ID, METADATA_PROCESS_ID_VALUE)
    metadataMap.put(METADATA_CREATED_DATETIME, response.headers.get("Date").get(0).toString)
    metadataMap.put(METADATA_CONTENT_LENGTH, response.headers.get("Content-Length").get(0).toString)
    metadataMap.put(METADATA_CONTENT_TYPE, response.headers.get("Content-Type").get(0).toString)

    val userMetadata = metadataMap
    metadata.setUserMetadata(userMetadata)
    val s3Object = new S3Object(key + ".json", fileInputStream, metadata)
    s3Client.sendObjectToS3(s3Object)
  }
}

object S3ResponseWriter {
  def apply(bdsS3Client: BdsS3Client): S3ResponseWriter = {
    new S3ResponseWriter(bdsS3Client)
  }
}
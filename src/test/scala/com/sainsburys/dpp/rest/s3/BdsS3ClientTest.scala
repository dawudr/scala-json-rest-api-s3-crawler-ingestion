package com.sainsburys.dpp.rest.s3

import java.io.InputStream
import java.util

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.{ObjectListing, ObjectMetadata, S3ObjectSummary}
import com.sainsburys.dpp.rest.config.ConfigPropertiesFactory
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar


class BdsS3ClientTest extends FunSuite with MockitoSugar {

  val amazonS3Client: AmazonS3Client = mock[AmazonS3Client]
  val s3 = BdsS3Client(ConfigPropertiesFactory.load(Array("config=application.conf")), amazonS3Client)


  test("testSendObjectsToS3") {
    val objectMetadata =  mock[ObjectMetadata]
    val inputStream =  mock[InputStream]
    val s3Object = new S3Object("key", inputStream, objectMetadata)
    val s3Objects =  List(s3Object)

    when(amazonS3Client.putObject("s3BucketName-test", s3Object.key, s3Object.value, s3Object.objectMetadata)).thenThrow(new RuntimeException("method putObject invoked"))

    var ex: Exception = null

    try {
      s3.sendObjectsToS3(s3Objects)
    } catch {
      case e: RuntimeException => ex = e;
    }

    assert (ex.getMessage.eq("method putObject invoked"))

  }

  test("testGetAllKeys") {

    val objectSummary = mock[S3ObjectSummary]
    val objectSummaries = new util.ArrayList[S3ObjectSummary]
    val objectListing = mock[ObjectListing]

    when(objectSummary.getKey).thenReturn("js-dpp3-bds-data-1-somefile.csv")
    objectSummaries.add(objectSummary)

    when(objectListing.getObjectSummaries).thenReturn(objectSummaries)

    when(amazonS3Client.listObjects("s3BucketName-test", "js-dpp3-bds-data-")).thenReturn(objectListing)

    val keys = s3.getAllKeys()

    assert(keys.size == 1)
    assert(keys.head == "js-dpp3-bds-data-1-somefile.csv")
  }

}

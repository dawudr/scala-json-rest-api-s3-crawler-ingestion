package com.sainsburys.dpp.rest.s3

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.sainsburys.dpp.rest.Loggable
import com.sainsburys.dpp.rest.config.ConfigProperties


class BdsS3Client private(private val s3: AmazonS3Client,
                          private val bucketName: String,
                          private val s3KeyPrefix: String) extends Loggable {

  logger.info("Connecting to Amazon S3")

  if (!s3.doesBucketExist(bucketName)) {
    logger.info("Creating S3 bucket: {}", bucketName)
    s3.createBucket(bucketName)
  }

  def sendObjectsToS3(s3Objects: List[S3Object]) = {
    s3Objects.par.foreach(s3Object => {
      logger.info("Sending {} to S3 bucket {}", s3Object.key, bucketName:Any)
      s3.putObject(bucketName, s3Object.key, s3Object.value, s3Object.objectMetadata)
      logger.info("File {} persisted", s3Object.key)
    })
  }

  def sendObjectToS3(s3Object: S3Object) = {
      logger.info("Sending {} to S3 bucket {}", s3Object.key, bucketName:Any)
      s3.putObject(bucketName, s3Object.key, s3Object.value, s3Object.objectMetadata)
      logger.info("File {} persisted", s3Object.key)
  }

  def getAllKeys() : Set[String] = {
    val s3KeysObj = s3.listObjects(bucketName, s3KeyPrefix).getObjectSummaries.toArray()
    s3KeysObj.map(obj => obj.asInstanceOf[S3ObjectSummary].getKey).toSet
  }

}

object BdsS3Client {
  def apply(configProperties: ConfigProperties, s3: AmazonS3Client = null): BdsS3Client = {
    if (null == s3) {
      new BdsS3Client(
        new AmazonS3Client(new BasicAWSCredentials(configProperties.s3AccessKey, configProperties.s3SecretKey)),
        configProperties.s3BucketName,
        configProperties.s3KeyPrefix)
    } else {
      new BdsS3Client(s3,
        configProperties.s3BucketName,
        configProperties.s3KeyPrefix)
    }
  }
}

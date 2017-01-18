package com.sainsburys.dpp.rest.s3

import java.io.InputStream

import com.amazonaws.services.s3.model.ObjectMetadata

class S3Object (val key: String, val value: InputStream, val objectMetadata: ObjectMetadata = new ObjectMetadata()) {
  objectMetadata.setContentLength(value.available())
}
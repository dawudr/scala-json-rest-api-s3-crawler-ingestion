package com.sainsburys.dpp.rest

import org.slf4j.LoggerFactory
/**
  * App Logging
  *
  * @author Dawud Rahman (dawud.rahman@sainsburys.co.uk)
  * @version 1.0
  * @since 10/01/2017
  */
trait Loggable {
  protected lazy val logger = LoggerFactory.getLogger(getClass)

  protected def logConfigMissingWarning(path: String) = {
    logger.warn("Configuration property [{}] not found. Using default value.", path)
  }
}

package com.sainsburys.dpp.rest.config

import com.typesafe.config.ConfigFactory

object ConfigurationFactory {

  def load (filePath : String) : Configuration = {
    new Configuration(ConfigFactory.load(filePath))
  }

}

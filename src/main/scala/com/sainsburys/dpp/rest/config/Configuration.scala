package com.sainsburys.dpp.rest.config

import com.sainsburys.dpp.rest.Loggable
import com.typesafe.config.Config

class Configuration(private val config: Config) extends Loggable {

  def getString(path: String) : String = {
    if (config.hasPath(path)) {
      config.getString(path)
    } else {
      logConfigMissingWarning(path)
      ""
    }
  }

  def getInt(path: String) : Int = {
    if (config.hasPath(path)) {
      config.getInt(path)
    } else {
      logConfigMissingWarning(path)
      0
    }
  }


}

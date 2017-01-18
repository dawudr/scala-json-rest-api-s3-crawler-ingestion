package com.sainsburys.dpp.rest.config

import org.scalatest.FunSuite

class ConfigPropertiesFactory$Test extends FunSuite {

  test("testLoadConfigFromFile") {

    val args = new Array[String](1)
    args(0) = "config=application.conf"
    val configProperties = ConfigPropertiesFactory.load(args)
    assert(configProperties.apiName.equals("storelocation"))
    assert(configProperties.apiUrl.equals("https://api.stores.sainsburys.co.uk/v1/stores/"))
    assert(configProperties.apiApiClientId.equals( "dpp"))
    assert(configProperties.apiLimit == 50)
    assert(configProperties.apiOffset == 50)
    assert(configProperties.apiMaxOffset == 10000)
    assert(configProperties.apiOutputPath.equals("output"))
    assert(configProperties.apiListNodeName.equals("results"))
  }

  test("testLoadConfigFromArguments") {

    val args = new Array[String](9)
    args(0) = "storelocation-test-new"
    args(1) = "api.webservice.url=https://test.api.stores.sainsburys.co.uk/v1/stores-new"
    args(2) = "api.webservice.param.api_client_id=dpp-test-new"
    args(3) = "api.webservice.param.limit=99"
    args(4) = "api.webservice.param.offset=99"
    args(5) = "api.webservice.param.max.offset=999"
    args(6) = "api.webservice.output.path=test/output/storelocation-new"
    args(7) = "api.webservice.list.nodename=results-test-new"
    args(8) = "config=application.conf"
    val configProperties = ConfigPropertiesFactory.load(args)
    assert(configProperties.apiName.equals("storelocation-test-new"))
    assert(configProperties.apiUrl.equals("https://test.api.stores.sainsburys.co.uk/v1/stores-new"))
    assert(configProperties.apiApiClientId.equals("dpp-test-new"))
    assert(configProperties.apiLimit == 99)
    assert(configProperties.apiOffset == 99)
    assert(configProperties.apiMaxOffset == 999)
    assert(configProperties.apiOutputPath.equals("test/output/storelocation-new"))
    assert(configProperties.apiListNodeName.equals("results-test-new"))
  }

}

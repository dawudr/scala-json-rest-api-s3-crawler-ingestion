name := "dpp-bds-httprest-client"

version := "1.0"

scalaVersion := "2.10.6"

val jacksonVersion = "2.8.4"

libraryDependencies ++= Seq(
  "com.jcraft" % "jsch" % "0.1.48" ,
  "com.typesafe" % "config" % "1.3.1",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.64" ,
  "org.slf4j" % "slf4j-simple" % "1.7.21",
  "org.slf4j" % "slf4j-api" % "1.7.21" ,
  "org.mockito" % "mockito-all" % "1.9.5",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % jacksonVersion,
  "org.scalatest" % "scalatest_2.10" % "3.0.1" % "test",
  "org.scalaj" %% "scalaj-http" % "2.3.0")

trapExit := false
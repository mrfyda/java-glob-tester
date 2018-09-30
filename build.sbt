name := """java-glob-tester"""
organization := "com.codacy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayService)
  .settings(
    inThisBuild(
      List(
        scalacOptions ++= Common.compilerFlags
      )))
  .settings(Common.genericSettings: _*)

scalaVersion := "2.12.7"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.codacy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.codacy.binders._"

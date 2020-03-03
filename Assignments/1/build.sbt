import Dependencies._

ThisBuild / scalaVersion     := "2.11.12"
ThisBuild / version          := "1.0"
ThisBuild / organization     := "edu.sjsu.selvia.andrew"
ThisBuild / organizationName := "AndrewSelviaSJSU"

lazy val root = (project in file("."))
  .settings(
    name := "Assignment 1",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.6",
    libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.5"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.

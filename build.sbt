name := "PubSubChatroom"

version := "1.0"

scalaVersion := "2.11.7"

lazy val akkaVersion = "2.4.0"
lazy val akkaStream = "2.11"
lazy val akkaHttp = "2.11"

libraryDependencies ++=
  Seq(
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
  )


libraryDependencies <+= scalaVersion("org.scala-lang" % "scala-compiler" % _ )
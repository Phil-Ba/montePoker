name := "montePoker"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.mavenLocal

val versionLog4j2 = "2.8.2"

libraryDependencies ++= Seq(
	//scala libs
	"io.monix" %% "monix" % "2.3.0",
	"com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
	//scala test
	"org.scalatest" %% "scalatest" % "3.0.1" % Test,
	"org.scalacheck" %% "scalacheck" % "1.13.4" % Test,
	//java libs
	"org.apache.logging.log4j" % "log4j-slf4j-impl" % versionLog4j2,
	"org.apache.logging.log4j" % "log4j-api" % versionLog4j2
)
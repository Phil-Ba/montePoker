name := "montePoker"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.mavenLocal

val log4j2Version = "2.8.2"

libraryDependencies ++= Seq(
	//scala libs
	"io.monix" %% "monix" % "2.3.0",
	"com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
	"org.scalatest" %% "scalatest" % "3.0.1" % "test",
	//java libs
	"org.apache.logging.log4j" % "log4j-slf4j-impl" % log4j2Version,
	"org.apache.logging.log4j" % "log4j-api" % log4j2Version
)
name := "montePoker"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.mavenLocal

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")

val versionLog4j2 = "2.8.2"

libraryDependencies ++= Seq(
	//scala libs
	"io.monix" %% "monix" % "2.3.0",
	"com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
	"org.scalatest" %% "scalatest" % "3.0.1" % "test",
	//java libs
	"org.apache.logging.log4j" % "log4j-slf4j-impl" % versionLog4j2,
	"org.apache.logging.log4j" % "log4j-api" % versionLog4j2
)
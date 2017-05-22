name := "montePoker"

version := "1.0"

scalaVersion := "2.12.2"

resolvers += Resolver.mavenLocal

libraryDependencies ++= Seq {
	"io.monix" %% "monix" % "2.3.0"
}
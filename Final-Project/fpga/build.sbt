name         := "AudioAccel"
version      := "0.1"
scalaVersion := "2.12.7"
val scalatestVersion  = "3.0.5"

val paradiseVersion = "2.1.0"
/** Macro Paradise **/
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)
addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full)

libraryDependencies ++= Seq(
  "edu.stanford.cs.dawn" %% "spatial" % "1.1-cs217",
  "org.scalatest" %% "scalatest" % scalatestVersion
)


name         := "WhisperScale"
version      := "0.1"
scalaVersion := "2.12.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "edu.stanford.cs.dawn" %% "spatial" % "1.1-cs217"
)

// Needed for @spatial macro, if you use it directly in WhisperScale.scala
val paradiseVersion = "2.1.0"
addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full) 
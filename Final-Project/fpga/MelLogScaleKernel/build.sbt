name         := "MelLogScaleKernel"
version      := "0.1"
scalaVersion := "2.12.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "edu.stanford.cs.dawn" %% "spatial" % "1.1-cs217"
)

// Needed for @spatial macro
val paradiseVersion = "2.1.0"
addCompilerPlugin("org.scalamacros" % "paradise" % paradiseVersion cross CrossVersion.full) 
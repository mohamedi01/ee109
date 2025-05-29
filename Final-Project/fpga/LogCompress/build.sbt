// fpga/LogCompress/build.sbt
// enablePlugins(Spatial)

name         := "LogCompress"
version      := "0.1"
scalaVersion := "2.12.7"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies ++= Seq(
  "edu.stanford.cs.dawn" %% "spatial" % "1.1-cs217"
)

// If you use @spatial directly, you also need Macro Paradise
addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
)

val commonsettings = Seq(
  version := "1.0.1",
  organization := "io.github.pityka",
  scalaVersion := "2.13.5",
  crossScalaVersions := Seq("2.12.13", "2.13.5")
)

lazy val root = project
  .in(file("."))
  .settings(commonsettings: _*)
  .settings(
    name := "overrepresentation",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % "test",
    libraryDependencies += "io.github.pityka" %% "fileutils" % "1.2.3"
  )

publishTo := sonatypePublishTo.value

pomExtra in Global := {
  <url>https://pityka.github.io/overrepresentation</url>
  <licenses>
    <license>
      <name>MIT</name>
      <url>https://opensource.org/licenses/MIT</url>
    </license>
  </licenses>
  <scm>
    <connection>scm:git:github.com/pityka/overrepresentation</connection>
    <developerConnection>scm:git:git@github.com:pityka/overrepresentation</developerConnection>
    <url>github.com/pityka/overrepresentation</url>
  </scm>
  <developers>
    <developer>
      <id>pityka</id>
      <name>Istvan Bartha</name>
      <url>https://pityka.github.io/overrepresentation/</url>
    </developer>
  </developers>
}

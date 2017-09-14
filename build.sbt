// build
autoScalaLibrary := false
crossPaths := false
Compile / compile / javacOptions ++= Seq("-g", "-parameters")

// override sbt-dynver (sbt-ci-release brings it, but we don't want it)
version := "9.9.0-scala-1"

// publish
name := "scala-asm"
organization := "org.scala-lang.modules"
homepage := Some(url("https://github.com/scala/scala-asm"))
licenses := Seq("BSD 3-clause" -> url("http://opensource.org/licenses/BSD-3-Clause"))
scmInfo := Some(ScmInfo(url("https://github.com/scala/scala-asm"), "scm:git:git@github.com:scala/scala-asm.git"))
pomExtra :=
  <developers>
    <developer>
      <id>lamp</id>
      <name>LAMP/EPFL</name>
    </developer>
    <developer>
      <id>Akka</id>
      <name>Akka</name>
    </developer>
  </developers>

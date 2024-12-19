// build
autoScalaLibrary := false
crossPaths := false
Compile / compile / javacOptions ++= Seq("-g", "-parameters")

// publish
name := "scala-asm"
organization := "org.scala-lang.modules"
sonatypeProfileName := "org.scala-lang"
homepage := Some(url("https://github.com/scala/scala-asm"))
licenses := Seq("BSD 3-clause" -> url("http://opensource.org/licenses/BSD-3-Clause"))
scmInfo := Some(ScmInfo(url("https://github.com/scala/scala-asm"),
  "scm:git:git@github.com:scala/scala-asm.git"))

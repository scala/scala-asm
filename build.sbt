name := "scala-asm"

organization := "org.scala-lang.modules"
sonatypeProfileName := "org.scala-lang"
homepage := Some(url("https://github.com/scala/scala-asm"))
licenses := Seq("BSD 3-clause" -> url("http://opensource.org/licenses/BSD-3-Clause"))
scmInfo := Some(ScmInfo(url("https://github.com/scala/scala-asm"),
  "scm:git:git@github.com:scala/scala-asm.git"))

// Otherwise the artifact has a dependency on scala-library
autoScalaLibrary := false

// Don't add `_<scala-version>` to the jar file name - it's a Java-only project, no Scala cross-versioning needed
crossPaths := false

Compile / compile / javacOptions ++= Seq("-g", "-parameters")

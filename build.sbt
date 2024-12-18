name := "scala-asm"

// Otherwise the artifact has a dependency on scala-library
autoScalaLibrary := false

// Don't add `_<scala-version>` to the jar file name - it's a Java-only project, no Scala cross-versioning needed
crossPaths := false

Compile / compile / javacOptions ++= Seq("-g", "-parameters")

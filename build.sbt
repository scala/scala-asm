import ScalaModulePlugin._

scalaModuleSettings
scalaModuleSettingsJVM

name := "scala-asm"

enablePlugins(GitVersioning)
lazy val AsmTag = """ASM_(\d+)_(\d+)(?:_(\d+))?(?:_([\w\d_]+))?(-\d+-[\w\d]+)?""".r
lazy val ScalaAsmTag = """v(.*)""".r
git.gitTagToVersionNumber := {
  case AsmTag(maj, min, pat, suf, des) =>
    // map a git-describe version built from an asm tag to a compatible version number
    val p = if (pat != null && pat != "") s".$pat" else ""
    val s = if (suf != null && suf != "") s"-$suf" else ""
    val d = if (des != null && des != "") des else ""
    Some(s"$maj.$min$p$s$d")
  case ScalaAsmTag(v) =>
    Some(v)
  case v =>
    throw new Error(s"Cannot get version number from git-describe: $v")
}
git.useGitDescribe := true

scalaVersionsByJvm in ThisBuild := {
  val vs = List("2.11.11")
  Map(
    6 -> vs.map(_ -> true),
    7 -> vs.map(_ -> false),
    8 -> vs.map(_ -> false),
    9 -> vs.map(_ -> false))
}

// Otherwise the artifact has a dependency on scala-library
autoScalaLibrary := false

// Don't add `_<scala-version>` to the jar file name - it's a Java-only project, no Scala cross-versioning needed
crossPaths := false

javacOptions ++= Seq("-g", "-source", "1.5", "-target", "1.6")

// javadoc fails if we pass all of the above
javacOptions in doc := Seq("-source", "1.5")

OsgiKeys.exportPackage := Seq(s"scala.tools.asm.*;version=${version.value}")

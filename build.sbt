lazy val V = _root_.scalafix.Versions
// Use a scala version supported by scalafix.
scalaVersion in ThisBuild := V.scala212

lazy val rules = project.settings(
  libraryDependencies ++= Seq(
    "ch.epfl.scala" %% "scalafix-core" % V.version,
    "com.github.masseguillaume" %% "scalameta-structure" % "0.1.0"
  )
)

lazy val input = project.settings(
  scalafixSourceroot := sourceDirectory.in(Compile).value
)

lazy val output = project
  .settings(
    scalaVersion := "2.13.0-M4"
  )

lazy val tests = project
  .settings(
    libraryDependencies += "ch.epfl.scala" % "scalafix-testkit" % V.version % Test cross CrossVersion.full,
    buildInfoPackage := "fix",
    buildInfoKeys := Seq[BuildInfoKey](
      "inputSourceroot" ->
        sourceDirectory.in(input, Compile).value,
      "outputSourceroot" ->
        sourceDirectory.in(output, Compile).value,
      "inputClassdirectory" ->
        classDirectory.in(input, Compile).value
    )
  )
  .dependsOn(input, rules)
  .enablePlugins(BuildInfoPlugin)




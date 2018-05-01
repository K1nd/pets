organization in ThisBuild := "ltd.k1nd"
organizationName in ThisBuild := "KIND Consulting Ltd."
organizationHomepage in ThisBuild := Some(url("https://k1nd.ltd"))

scalaVersion in ThisBuild := scala212

lazy val pets = (project in file("."))
  .aggregate(bones, dog)
  .dependsOn(bones, dog)
  .withGpgCreds
  .settings(
    name := "pets",
    scalaSettings
  )

lazy val bones = project.settings(
  name := "pets-bones",
  scalaSettings
).withGpgCreds

lazy val dog = project.settings(
  name := "pets-dog",
  scalaSettings,
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "1.0.1",
    "org.typelevel" %% "cats-effect" % "0.10",
    "org.typelevel" %% "machinist" % "0.6.4"
  ),
  addCompilerPlugin("org.spire-math" % "kind-projector" % "0.9.6" cross CrossVersion.binary),
  libraryDependencies ++= (scalaBinaryVersion.value match {
    case "2.10" =>
      compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full) :: Nil
    case _ =>
      Nil
  })
).withGpgCreds

compile in Compile := (compile in Compile).dependsOn(dependencyUpdates).value
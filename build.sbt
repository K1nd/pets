lazy val pets = (project in file("."))
  .aggregate(bones, dog)
  .dependsOn(bones, dog)
  .withCommonSettings
  .settings(
    name := "pets"
  )
  .withReleaseSettings

lazy val bones = project
  .settings(
    name := "pets-bones"
  )
  .withCommonSettings
  .withReleaseSettings

lazy val dog = project
  .settings(
    name := "pets-dog",
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
  )
  .withCommonSettings
  .withReleaseSettings
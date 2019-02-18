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
      "org.typelevel" %% "cats-core" % "1.6.0",
      "org.typelevel" %% "cats-effect" % "1.2.0",
      "org.typelevel" %% "machinist" % "0.6.6"
    )
  )
  .withCommonSettings
  .withReleaseSettings
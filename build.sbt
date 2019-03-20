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
    name := "pets-bones",
    libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.3",
      "org.typelevel" %% "cats-effect" % "1.2.0",
      "org.typelevel" %% "cats-mtl-core" % "0.5.0",
      "com.lihaoyi" %% "sourcecode" % "0.1.5",
      "org.slf4j" % "slf4j-api" % "1.7.26"
    )
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

git.uncommittedSignifier := None

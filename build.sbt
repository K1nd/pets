organization in ThisBuild := "ltd.k1nd"
organizationName := "KIND Consulting Ltd."
organizationHomepage := Some(url("https://k1nd.ltd"))

version in ThisBuild := "0.1"

scalaVersion in ThisBuild := "2.12.4"

lazy val pets = (project in file("."))
  .aggregate(bones, dog)
  .settings(
    name := "pets",
    scalaSettings
  )

lazy val bones = project.settings(
  name := "pets-bones",
  scalaSettings
)

lazy val dog = project.settings(
  name := "pets-dog",
  scalaSettings,
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "1.0.1",
    "org.typelevel" %% "cats-effect" % "0.10"
  )
)

lazy val scala210 = "2.10.7"
lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.4"

lazy val scalaSettings = Seq(
  scalaVersion := scala212,
  crossScalaVersions := Seq(scala210, scala211, scala212),
  libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "3.0.5",
    "org.scalactic" %% "scalactic" % "3.0.5",
    "org.scalacheck" %% "scalacheck" % "1.13.5"
  ).map(_ % Test),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-target:jvm-1.8",
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture",
    "-Ywarn-unused-import",
    "-Ywarn-unused"
  ).filter {
    case "-Ywarn-unused-import" if scalaVersion.value startsWith "2.10" => false
    case "-Ywarn-unused" if !(scalaVersion.value startsWith "2.12")     => false
    case _                                                              => true
  },
  scalacOptions in (Compile, console) --= Seq("-Xlint",
                                              "-Ywarn-unused",
                                              "-Ywarn-unused-import"),
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value
)

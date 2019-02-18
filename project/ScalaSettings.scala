import sbt._
import sbt.Keys._

object ScalaSettings extends AutoPlugin {
  object autoImport {
    implicit class SettingsOps(val proj: Project) extends AnyVal {
      def withCommonSettings: Project = proj.settings(
        commonSettings
      )
    }
  }

  lazy val scalatestVersion = "3.0.5"

  lazy val commonSettings = Seq(
    scalaVersion := "2.12.8",
    organization := "ltd.k1nd",
    organizationName := "KIND Consulting Ltd.",
    organizationHomepage := Some(url("https://k1nd.ltd")),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9"),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % scalatestVersion,
      "org.scalactic" %% "scalactic" % scalatestVersion,
      "org.scalacheck" %% "scalacheck" % "1.14.0",
      "org.scalamock" %% "scalamock" % "4.1.0"
    ).map(_ % Test),
    scalacOptions ++= Seq(
      "-Ypartial-unification",
      "-deprecation", // Emit warning and location for usages of deprecated APIs.
      "-encoding",
      "utf-8", // Specify character encoding used by source files.
      "-explaintypes", // Explain type errors in more detail.
      "-feature", // Emit warning and location for usages of features that should be imported explicitly.
      "-language:existentials", // Existential types (besides wildcard types) can be written and inferred
      "-language:experimental.macros", // Allow macro definition (besides implementation and application)
      "-language:higherKinds", // Allow higher-kinded types
      "-language:implicitConversions" // Allow definition of implicit functions called views
    )
  )
}

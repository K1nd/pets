import com.timushev.sbt.updates.UpdatesKeys.dependencyUpdates
import com.timushev.sbt.updates.UpdatesPlugin.autoImport.dependencyUpdatesFailBuild
import com.typesafe.sbt.GitBranchPrompt
import sbt._
import sbt.Keys._

import scala.sys.process.Process

object ScalaSettings extends AutoPlugin {
  object autoImport {
    implicit class SettingsOps(val proj: Project) extends AnyVal {
      def withCommonSettings: Project =
        proj
          .settings(
            commonSettings
          )
          .enablePlugins(GitBranchPrompt)
    }
  }

  lazy val scalatestVersion = "3.0.5"

  lazy val commonSettings = Seq(
    scalaVersion := "2.12.8",
    organization := "ltd.k1nd",
    organizationName := "KIND Consulting Ltd.",
    organizationHomepage := Some(url("https://k1nd.ltd")),
    gitBranch := Process("git rev-parse --abbrev-ref HEAD").lineStream.head,
    dependencyUpdatesFailBuild := gitBranch.value != "master",
    compile in Compile := (compile in Compile)
      .dependsOn(dependencyUpdates)
      .value,
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

  private val gitBranch = settingKey[String]("Determines current git branch")
}

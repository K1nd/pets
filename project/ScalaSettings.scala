import com.timushev.sbt.updates.UpdatesKeys.dependencyUpdates
import com.timushev.sbt.updates.UpdatesPlugin.autoImport.dependencyUpdatesFailBuild
import com.typesafe.sbt.GitPlugin.autoImport.git
import com.typesafe.sbt.{GitBranchPrompt, GitVersioning}
import com.typesafe.sbt.SbtGit.GitKeys
import sbt._
import sbt.Keys._
import sbtrelease.ReleasePlugin.autoImport.{releaseTagName, releaseVersionFile}
import sbtrelease.Version
import sbtrelease.Version.Bump

object ScalaSettings extends AutoPlugin {
  object autoImport {
    implicit class SettingsOps(val proj: Project) extends AnyVal {
      def withCommonSettings: Project =
        proj
          .settings(
            commonSettings,
            gitVersionMatcher
          )
          .enablePlugins(GitBranchPrompt, GitVersioning)
    }
  }

  lazy val scalatestVersion = "3.0.7"

  lazy val commonSettings = Seq(
    scalaVersion := "2.12.8",
    organization := "ltd.k1nd",
    organizationName := "KIND Consulting Ltd.",
    organizationHomepage := Some(url("https://k1nd.ltd")),
    dependencyUpdatesFailBuild := GitKeys.gitReader.value
      .withGit(_.branch) != "master",
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

  lazy val gitVersionMatcher = Seq(
    git.useGitDescribe := true,
    releaseVersionFile := new sbt.File("/dev/null"),
    releaseTagName := s"v${(version in ThisBuild).value}",
    git.gitTagToVersionNumber := { tag =>
      def calculateBump(msg: String): Bump = msg.toUpperCase match {
        case major if major.contains("|BREAKING")    => Bump.Major
        case feature if feature.contains("|FEATURE") => Bump.Minor
        case _                                       => Bump.Bugfix
      }

      for {
        tagStr <- Option(tag) collect {
          case VersionRegex(versionStr) => versionStr
        }
        headCommitMessage = git.gitHeadMessage.value.getOrElse("")
        bump = calculateBump(headCommitMessage)
        suffix = git.gitCurrentTags.value
          .find(_ == tag)
          .map(_ => "")
          .getOrElse("-SNAPSHOT")
        version <- Version(tagStr)
        bumped = version.bump(bump).string
      } yield s"$bumped$suffix"
    }
  )

  lazy val VersionRegex = "v?([0-9][0-9\\.]*).*".r
}

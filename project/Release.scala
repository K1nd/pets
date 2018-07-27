import com.typesafe.sbt.SbtPgp.autoImportImpl.PgpKeys
import sbt.Keys.{publishTo, version}
import sbt.{AutoPlugin, Project}
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._
import xerial.sbt.Sonatype.autoImport.sonatypePublishTo

object Release extends AutoPlugin {

  object autoImport {

    implicit class ReleaseOps(val proj: Project) extends AnyVal {
      def withReleaseSettings: Project = proj.settings(
        releaseSettings,
        releaseTagName := s"${version.value}"
      )
    }

  }

  lazy val releaseSettings = Seq(
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    releaseCrossBuild := true,
    releaseTagComment := s"Releasing ${(version).value} [ci skip]",
    releaseCommitMessage := s"Setting version to ${(version).value} [ci skip]",
    releaseVersionFile := new sbt.File("/dev/null"),

    releaseProcess := Seq[ReleaseStep](
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      tagRelease,
      publishArtifacts,
      releaseStepCommand("sonatypeReleaseAll"),
      pushChanges
    ),

    publishTo := sonatypePublishTo.value
  )
}

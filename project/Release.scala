import com.typesafe.sbt.SbtPgp.autoImportImpl.PgpKeys
import sbt.{AutoPlugin, Project}
import sbt.Keys.{publishTo, version}
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._
import xerial.sbt.Sonatype.autoImport.sonatypePublishTo

object Release extends AutoPlugin {

  object autoImport {

    implicit class ReleaseOps(val proj: Project) extends AnyVal {
      def withReleaseSettings: Project = proj.settings(
        releaseSettings
      )
    }

  }

  lazy val releaseSettings = Seq(
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    releaseCrossBuild := true,
    releaseTagComment := s"Releasing ${(version).value} [ci skip]",
    releaseCommitMessage := s"Setting version to ${(version).value} [ci skip]",

    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      setNextVersion,
      commitNextVersion,
      releaseStepCommand("sonatypeReleaseAll"),
      pushChanges
    ),

    publishTo := sonatypePublishTo.value
  )
}

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
        releaseSettings
      )
    }

  }

  lazy val releaseSettings = Seq(
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      setReleaseVersion,
      releaseStepCommand("sonatypeReleaseAll"),
      tagRelease,
      pushChanges
    ),
    publishTo := sonatypePublishTo.value
  )
}

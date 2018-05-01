import ReleaseTransformations._

releasePublishArtifactsAction in ThisBuild := PgpKeys.publishSigned.value
releaseCrossBuild in ThisBuild := true // true if you cross-build the project for multiple Scala versions
releaseTagComment in ThisBuild    := s"Releasing ${(version in ThisBuild).value} [ci skip]"
releaseCommitMessage in ThisBuild := s"Setting version to ${(version in ThisBuild).value} [ci skip]"

releaseProcess in ThisBuild := Seq[ReleaseStep](
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
)

publishTo in ThisBuild := sonatypePublishTo.value
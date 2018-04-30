import ReleaseTransformations._

releasePublishArtifactsAction in ThisBuild := PgpKeys.publishSigned.value
releaseCrossBuild in ThisBuild := true // true if you cross-build the project for multiple Scala versions
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

// POM settings for Sonatype


// Add sonatype repository settings
publishTo in ThisBuild := sonatypePublishTo.value
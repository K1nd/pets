import ReleaseTransformations._

releaseProcess in ThisBuild := Seq[ReleaseStep](
  checkSnapshotDependencies,              // : ReleaseStep
  inquireVersions,                        // : ReleaseStep
  runClean,                               // : ReleaseStep
  runTest,                                // : ReleaseStep
  setReleaseVersion,                      // : ReleaseStep
  commitReleaseVersion,                   // : ReleaseStep, performs the initial git checks
  tagRelease,                             // : ReleaseStep
  publishArtifacts,                       // : ReleaseStep, checks whether `publishTo` is properly set up
  setNextVersion,                         // : ReleaseStep
  commitNextVersion,                      // : ReleaseStep
  pushChanges                             // : ReleaseStep, also checks that an upstream branch is properly configured
)

// POM settings for Sonatype
homepage in ThisBuild := Some(url("https://github.com/K1nd/pets"))
scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/K1nd/pets"),
  "git@github.com:K1nd/pets.git"))
developers in ThisBuild := List(Developer("wunderk1nd-e",
  "wunderk1nd-e",
  "elias@k1nd.ltd",
  url("https://github.com/wunderk1nd-e")))
licenses in ThisBuild += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle in ThisBuild := true

// Add sonatype repository settings
publishTo in ThisBuild := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)
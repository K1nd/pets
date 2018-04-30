import LoadCredentials._
import xerial.sbt.Sonatype._

sonatypeProfileName in ThisBuild := "ltd.k1nd"

publishMavenStyle in ThisBuild := true

licenses in ThisBuild := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

sonatypeProjectHosting in ThisBuild := Some(GitHubHosting("K1nd", "pets", "elias@k1nd.ltd"))

// or if you want to set these fields manually
homepage in ThisBuild := Some(url("https://github.com/K1nd/pets"))

scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/K1nd/pets"),
  "git@github.com:K1nd/pets.git"))

developers in ThisBuild := List(Developer("wunderk1nd-e",
  "wunderk1nd-e",
  "elias@k1nd.ltd",
  url("https://github.com/wunderk1nd-e")))

credentials in ThisBuild ++= loadCredentials()
sonatypeProfileName := "ltd.k1nd"

publishMavenStyle := true

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

import xerial.sbt.Sonatype._
sonatypeProjectHosting := Some(GitHubHosting("K1nd", "pets", "elias@k1nd.ltd"))

// or if you want to set these fields manually
homepage in ThisBuild := Some(url("https://github.com/K1nd/pets"))

scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/K1nd/pets"),
  "git@github.com:K1nd/pets.git"))

developers in ThisBuild := List(Developer("wunderk1nd-e",
  "wunderk1nd-e",
  "elias@k1nd.ltd",
  url("https://github.com/wunderk1nd-e")))
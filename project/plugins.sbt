resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.4")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")

addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.4")

addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.12")

addSbtPlugin("org.lyranthe.sbt" % "partial-unification" % "1.1.0")

addSbtPlugin("org.tpolecat" % "tut-plugin" % "0.6.4")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")
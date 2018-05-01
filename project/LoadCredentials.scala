import sbt._

object LoadCredentials{
  lazy val envVars = System.getenv()

  lazy val gpgPassphrase = Option(envVars.get("PGP_PASSPHRASE")).map(_.toCharArray)

  lazy val sonatypeCredentials: List[Credentials] =
      (for {
    username <- Option(envVars.get("SONATYPE_USERNAME"))
    password <- Option(envVars.get("SONATYPE_PASSWORD"))
  } yield
    Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      username,
      password)
    ).toList
}
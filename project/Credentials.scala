import sbt._

object LoadCredentials {
  private lazy val envVars = System.getenv()

  def loadCredentials(): List[Credentials] =
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
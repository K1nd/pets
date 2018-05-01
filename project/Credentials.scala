import sbt._

object LoadCredentials extends AutoPlugin{

  object autoImport{
    val log = streams.value.log

    log.error(System.getenv())

    implicit class CredentialOps(val proj: Project) extends AnyVal {
      def withGpgCreds: Project = proj.settings(
        useGpg := false,
        pgpPassphrase := gpgPassphrase
      )
    }
  }

  private lazy val envVars = System.getenv()

  lazy val gpgPassphrase = Option(envVars.get("gpgpassphrase")).map(_.toCharArray)

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
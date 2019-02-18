package ltd.k1nd.pets.bones.log
import sourcecode.Enclosing

trait Logger[F[_]] {
  def trace(msg: String)(implicit path: Enclosing): F[Unit]
  def debug(msg: String)(implicit path: Enclosing): F[Unit]
  def info(msg: String)(implicit path: Enclosing): F[Unit]
  def warn(msg: String)(implicit path: Enclosing): F[Unit]
  def error(msg: String)(implicit path: Enclosing): F[Unit]
  def error(msg: String, ex: Throwable)(implicit path: Enclosing): F[Unit]

  def trace(msg: F[String])(implicit path: Enclosing): F[Unit]
  def debug(msg: F[String])(implicit path: Enclosing): F[Unit]
  def info(msg: F[String])(implicit path: Enclosing): F[Unit]
  def warn(msg: F[String])(implicit path: Enclosing): F[Unit]
  def error(msg: F[String])(implicit path: Enclosing): F[Unit]
  def error(msg: F[String], ex: F[Throwable])(implicit path: Enclosing): F[Unit]
}

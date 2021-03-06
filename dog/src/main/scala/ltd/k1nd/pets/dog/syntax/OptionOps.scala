package ltd.k1nd.pets.dog.syntax

import cats.Applicative
import cats.data.OptionT
import ltd.k1nd.pets.dog.syntax.OptionOps.OptionSyntax

trait OptionOps {
  implicit def createOptionSyntax[T](opt: Option[T]): OptionSyntax[T] =
    new OptionSyntax[T](opt)
}

object OptionOps {
  implicit final class OptionSyntax[T](val opt: Option[T]) extends AnyVal {
    def toOptionT[F[_]: Applicative] = OptionT.fromOption[F](opt)
  }
}

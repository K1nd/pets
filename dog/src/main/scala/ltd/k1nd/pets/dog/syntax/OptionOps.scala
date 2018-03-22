package ltd.k1nd.pets.dog.syntax

import cats.Applicative
import cats.data.OptionT
import ltd.k1nd.pets.dog.syntax.OptionOps.OptionSyntax

trait OptionOps {
  implicit def createOptionOps[T](opt: Option[T]): OptionSyntax[T] = new OptionSyntax[T](opt)
}

object OptionOps{
  implicit class OptionSyntax[T](val opt: Option[T]) extends AnyVal{
    def toOptionT[F[_]: Applicative] = OptionT.fromOption[F](opt)
  }
}
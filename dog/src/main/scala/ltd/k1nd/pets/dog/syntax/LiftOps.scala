package ltd.k1nd.pets.dog.syntax

import cats.Functor
import cats.data.{EitherT, OptionT}
import ltd.k1nd.pets.dog.syntax.LiftOps.LiftSyntax

trait LiftOps {
  def createLiftSyntax[F[_], T](toLift: F[T]): LiftSyntax[F, T] =
    new LiftSyntax(toLift)
}

object LiftOps {
  implicit class LiftSyntax[F[_], T](val toLift: F[T]) extends AnyVal {
    def liftEitherT[L](implicit m: Functor[F]): EitherT[F, L, T] =
      EitherT.liftF(toLift)
    def liftIntoOptionT(implicit f: Functor[F]): OptionT[F, T] =
      OptionT.liftF[F, T](toLift)
  }
}

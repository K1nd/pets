package ltd.k1nd.pets.dog.syntax

import cats.{Applicative, ApplicativeError}
import cats.syntax.either._
import ltd.k1nd.pets.dog.syntax.BooleanOps.BooleanSyntax

trait BooleanOps {
  implicit def createBooleanOps(b: Boolean): BooleanSyntax = new BooleanSyntax(b)
}

object BooleanOps{
  implicit final class BooleanSyntax(val b: Boolean) extends AnyVal{
    def asErrorMonad[F[_]]: PartiallyAppliedErrM[F] = new PartiallyAppliedErrM[F](b)
    def ifElseThen[T](ifFalse: => T, ifTrue: => T): T = if(b) ifTrue else ifFalse
    def whenA[F[_]: Applicative, A](f: => F[A]): F[Unit] = Applicative[F].whenA(b)(f)
    def toEither[L, R](l: => L, r: => R): Either[L, R] = ifElseThen(l.asLeft, r.asRight)
  }

  protected class PartiallyAppliedErrM[F[_]](val b: Boolean) extends AnyVal{
    def apply[L, R](l: => L, r: => R = ())(implicit appErr: ApplicativeError[F, L]): F[R] =
      new BooleanSyntax(b).ifElseThen(appErr.raiseError[R](l), appErr.pure(r))
  }
}
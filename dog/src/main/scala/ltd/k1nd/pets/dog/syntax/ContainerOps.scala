package ltd.k1nd.pets.dog.syntax

import cats.Functor
import cats.arrow.FunctionK
import cats.data.EitherT
import ltd.k1nd.pets.dog.syntax.ContainerOps.ContainerSyntax

trait ContainerOps {
  implicit def createContainerOps[F[_], T](valueInContext: F[T]): ContainerSyntax[F, T] = new ContainerSyntax(valueInContext)
}

object ContainerOps{
  implicit final class ContainerSyntax[F[_], T](val valueInContext: F[T]) extends AnyVal {
    def liftEitherT[L](implicit m: Functor[F]): EitherT[F, L, T] =
      EitherT.liftF(valueInContext)

    def transform[O[_]](implicit nat: FunctionK[F, O]): O[T] = nat(valueInContext)
  }
}
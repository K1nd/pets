package ltd.k1nd.pets.dog.syntax

import cats.arrow.FunctionK
import cats.data.{EitherT, OptionT}
import cats.Functor
import ltd.k1nd.pets.dog.syntax.ContainerOps.ContainerSyntax

trait ContainerOps {
  implicit def createContainerSyntax[F[_], T](
      valueInContext: F[T]): ContainerSyntax[F, T] =
    new ContainerSyntax(valueInContext)
}

object ContainerOps {
  implicit final class ContainerSyntax[F[_], T](val valueInContext: F[T])
      extends AnyVal {
    def liftEitherT[L](implicit m: Functor[F]): EitherT[F, L, T] =
      EitherT.liftF(valueInContext)

    def transform[O[_]](implicit nat: FunctionK[F, O]): O[T] =
      nat(valueInContext)

    def liftIntoOptionT(implicit f: Functor[F]): OptionT[F, T] =
      OptionT.liftF[F, T](valueInContext)
  }
}

package ltd.k1nd.pets.dog.syntax

import cats.effect.IO
import ltd.k1nd.pets.dog.syntax.FutureOps.FutureSyntax

import scala.concurrent.Future

trait FutureOps {
  implicit def createFutureSyntax[L, R](
      futVal: => Future[R]): FutureSyntax[Nothing, R] =
    new FutureSyntax(futVal)
}

object FutureOps {
  implicit final class FutureSyntax[L, R](futVal: => Future[R]) {
    def toIO: IO[R] =
      IO.fromFuture(IO(futVal))
  }

}

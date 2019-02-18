package ltd.k1nd.pets.dog.syntax

import cats.effect.Sync
import ltd.k1nd.pets.dog.syntax.SyncOps.SyncSyntax
import machinist.DefaultOps

trait SyncOps {
  implicit def createSyncSyntax[T](value: T): SyncSyntax[T] =
    new SyncSyntax[T](value)
}

object SyncOps {
  implicit final class SyncSyntax[T](val lhs: T) extends AnyVal {
    def delay[F[_]: Sync]: F[T] = macro DefaultOps.unopWithEv[Sync[F], F[T]]
  }
}

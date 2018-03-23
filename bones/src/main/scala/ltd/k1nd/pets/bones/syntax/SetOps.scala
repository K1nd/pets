package ltd.k1nd.pets.bones.syntax

import ltd.k1nd.pets.bones.syntax.SetOps.SetSyntax

trait SetOps {
  implicit def toOps[T](set: Set[T]): SetSyntax[T] = new SetSyntax[T](set)
}

object SetOps {
  implicit final class SetSyntax[T](val set: Set[T]) extends AnyVal {
    def doesNotContain(elem: T): Boolean = !set.contains(elem)
  }
}

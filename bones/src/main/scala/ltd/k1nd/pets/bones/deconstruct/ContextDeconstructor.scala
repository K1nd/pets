package ltd.k1nd.pets.bones.deconstruct

import cats.Show
import shapeless.{
  :+:,
  ::,
  CNil,
  Coproduct,
  Generic,
  HList,
  HNil,
  LabelledGeneric,
  Lazy,
  Refute,
  Witness
}
import cats.syntax.show._
import shapeless.labelled.FieldType

trait ContextDeconstructor[T] {
  def deconstruct(t: T): Map[List[String], String]
}

//Needs Show[T] instances for leaf nodes available
object ContextDeconstructor {
  def apply[T](implicit cd: ContextDeconstructor[T]): ContextDeconstructor[T] =
    cd

  implicit def leafDeconstructor[T: Show](
      implicit noOtherDeconstructor: Refute[ContextDeconstructor[T]])
    : ContextDeconstructor[T] =
    (t: T) => Map(Nil -> t.show)

  implicit object HnilDeconstructor extends ContextDeconstructor[HNil] {
    override def deconstruct(t: HNil): Map[List[String], String] = Map.empty
  }

  implicit def consDeconstructor[FieldValue, FieldName <: Symbol, Tl <: HList](
      implicit wit: Witness.Aux[FieldName],
      head: Lazy[ContextDeconstructor[FieldValue]],
      tail: Lazy[ContextDeconstructor[Tl]]
  ): ContextDeconstructor[FieldType[FieldName, FieldValue] :: Tl] =
    (t: FieldType[FieldName, FieldValue] :: Tl) => {
      val fieldName = wit.value.name
      head.value.deconstruct(t.head).map {
        case (keys, value) =>
          keys.::(fieldName) -> value
      } ++ tail.value.deconstruct(t.tail)
    }

  implicit object CNilDeconstructor extends ContextDeconstructor[CNil] {
    override def deconstruct(t: CNil): Map[List[String], String] = Map.empty
  }

  implicit def cconsDeconstructor[H, Tl <: Coproduct](
      implicit h: Lazy[ContextDeconstructor[H]],
      tl: Lazy[ContextDeconstructor[Tl]]): ContextDeconstructor[H :+: Tl] =
    (t: H :+: Tl) => t.eliminate(h.value.deconstruct, tl.value.deconstruct)

  implicit def recordDeconstructor[T, Repr](
      implicit gen: Lazy[LabelledGeneric.Aux[T, Repr]],
      evidence: Repr <:< HList,
      cd: Lazy[ContextDeconstructor[Repr]]): ContextDeconstructor[T] =
    (t: T) => cd.value.deconstruct(gen.value.to(t))

  implicit def coproductDeconstructor[T, Repr](
      implicit gen: Lazy[Generic.Aux[T, Repr]],
      evidence: Repr <:< Coproduct,
      cd: Lazy[ContextDeconstructor[Repr]]): ContextDeconstructor[T] =
    (t: T) => cd.value.deconstruct(gen.value.to(t))
}

package ltd.k1nd.pets.dog.syntax

import cats.arrow.FunctionK
import cats.data.OptionT
import cats.instances.list._
import cats.{Functor, Id, ~>}
import org.scalatest.{Matchers, WordSpec}

class ContainerOpsSpec extends WordSpec with Matchers {
  "liftEitherT" should {
    "Inject an either into a provided Functor" in {
      val funky = List(123)
      val lifted = funky.liftEitherT

      val expected = List(Right(123))

      lifted.value should equal(expected)
    }
  }

  "liftIntoOptionT" should {
    "Inject an option into a provided Functor" in {
      val funky = List(123)
      val lifted = funky.liftOptionT

      val expected = List(Option(123))

      lifted.value should equal(expected)
    }
  }

  "transform" should {
    "allow you to use a natural transformation in scope to transform one effect into another" in {

      implicit def fk[F[_]: Functor]: F ~> OptionT[F, ?] =
        new FunctionK[F, OptionT[F, ?]] {
          override def apply[A](fa: F[A]): OptionT[F, A] = OptionT.liftF(fa)
        }

      val funky = List(123)
      val lifted: OptionT[List, Int] = funky.transform

      val expected = List(Option(123))

      lifted.value should equal(expected)
    }
  }
}

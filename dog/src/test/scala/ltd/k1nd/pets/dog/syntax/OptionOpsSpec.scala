package ltd.k1nd.pets.dog.syntax

import cats.effect.IO
import org.scalatest.{Matchers, WordSpec}

class OptionOpsSpec extends WordSpec with Matchers {
  "OptionOps" should {
    "Wrap a None with an Applicative" in {
      val none: Option[Int] = None
      val lifted = none.toOptionT[IO]
      val expected = IO.pure(none)

      lifted.value should equal(expected)
    }

    "Wrap a Some with an Applicative" in {
      val some: Option[Int] = Option(123)
      val lifted = some.toOptionT[IO]
      val expected = IO.pure(some)

      lifted.value should equal(expected)
    }
  }
}

package ltd.k1nd.pets.dog.syntax


import cats.Id
import cats.implicits.catsStdInstancesForEither
import java.util.concurrent.atomic.AtomicBoolean
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{EitherValues, Matchers, WordSpec}

class BooleanOpsSpec
    extends WordSpec
    with GeneratorDrivenPropertyChecks
    with Matchers
    with EitherValues {

  "toApplicativeError" should {
    "successfully infer its type arguments" in {
      forAll { bool: Boolean =>
        "val x: Either[Int, Unit] = bool.toApplicativeError(1337)" should compile
      }
    }

    "turn into an context with an error on false and take the other value by name" in {
      val bool = false

      val result = bool.toApplicativeError(1337, fail())

      result.left.value should equal(1337)
    }

    "turn into a success on right and take the other value by name" in {
      val bool = true

      val successValue = "Great success!!"

      val result = bool.toApplicativeError(fail(), successValue)

      result.right.value should equal(successValue)
    }
  }

  "ifElseThen" should {
    "run left on false" in {
      false.ifElseThen((), fail()) should equal(())
    }

    "run right on true" in {
      true.ifElseThen(fail(), ()) should equal(())
    }
  }

  "toEither" should {
    "turn into a left on false" in {
      false.toEither((), ()).left.value should be(())
    }

    "turn into a right on true" in {
      true.toEither((), ()).right.value should be(())
    }
  }

  "whenA" should {
    "only run side effects when true" in {
      val bool = new AtomicBoolean(false)

      def activate(): Id[Unit] = bool.set(true)

      false.whenA(activate())

      bool.get() should equal(false)

      true.whenA(activate())

      bool.get() should equal(true)
    }
  }
}

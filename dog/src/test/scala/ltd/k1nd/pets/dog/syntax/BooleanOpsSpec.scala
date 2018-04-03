package ltd.k1nd.pets.dog.syntax

import cats.implicits.catsStdInstancesForEither
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import scala.util.Either

class BooleanOpsSpec extends FlatSpec
with GeneratorDrivenPropertyChecks
with Matchers with EitherValues {

  "toApplicativeError" should "successfully infer its type arguments" in {
    forAll{ bool: Boolean =>
      "val x: Either[Int, Unit] = bool.toApplicativeError(1337)" should compile
    }
  }

  it should "turn into an context with an error on false" in {
    val bool = false

    val leftMessage = "Ran left"
    var state = ""

    def left(): Int = {
      state += leftMessage
      1337
    }

    def right(): Unit = {
      state += "Ran right"
      ()
    }

    val result = bool.toApplicativeError(left(), right())

    result.left.value should equal(1337)
    state should equal(leftMessage)
  }

}

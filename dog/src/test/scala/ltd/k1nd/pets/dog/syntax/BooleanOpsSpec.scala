package ltd.k1nd.pets.dog.syntax

import cats.implicits.catsStdInstancesForEither
import org.scalatest.{EitherValues, FlatSpec, Matchers}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import scala.util.Either

class BooleanOpsSpec
    extends FlatSpec
    with GeneratorDrivenPropertyChecks
    with Matchers
    with EitherValues {

  "toApplicativeError" should "successfully infer its type arguments" in {
    forAll { bool: Boolean =>
      "val x: Either[Int, Unit] = bool.toApplicativeError(1337)" should compile
    }
  }

  it should "turn into an context with an error on false and take the other value by name" in {
    val bool = false

    val result = bool.toApplicativeError(1337, fail())

    result.left.value should equal(1337)
  }

  it should "turn into a success on right and take the other value by name" in {
    val bool = true

    val successValue = "Great success!!"

    val result = bool.toApplicativeError(fail(), successValue)

    result.right.value should equal(successValue)
  }

}

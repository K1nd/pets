package ltd.k1nd.pets.bones.syntax

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers, OptionValues}

class SetOpsSpec
    extends FlatSpec
    with GeneratorDrivenPropertyChecks
    with Matchers
    with OptionValues {

  val setGenerator = Gen.nonEmptyContainerOf[Set, Int](Gen.chooseNum(1, 100))

  "A set" should "return false for not containing its head element" in {
    forAll(setGenerator) { set =>
      val elemInSet = set.headOption
      elemInSet.map(set.doesNotContain).value should be(false)
    }
  }

  "A set of positive numbers" should "return true for not containing a negative number" in {
    forAll(setGenerator) { set =>
      set.doesNotContain(-1) should be(true)
    }
  }
}

package ltd.k1nd.pets.bones.syntax

import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

class SetOpsSpec
    extends FlatSpec
    with GeneratorDrivenPropertyChecks
    with Matchers {

  "simple test" should "pass" in {
    val s = Set(1, 2, 3)
    s.doesNotContain(4) should equal(true)
  }

}

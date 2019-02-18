package ltd.k1nd.pets.bones.deconstruct

import cats.Show
import cats.implicits._
import org.scalatest.{FlatSpec, Matchers}

class ContextDeconstructorSpec extends FlatSpec with Matchers {
  "deconstructor" should "work in the simple case" in {
    case class Test(thisIsX: Long, thisIsY: String)
    val expected: Map[List[String], String] =
      Map[List[String], String](List("thisIsX") -> "5", List("thisIsY") -> "hi")
    ContextDeconstructor[Test].deconstruct(Test(5, "hi")) should equal(expected)
  }

  it should "work in the nested case" in {
    case class Test(thisIsX: Long)
    case class NestTest(notNested: String, nested: Test)

    val expected = Map[List[String], String](List("notNested") -> "hi",
                                             List("nested", "thisIsX") -> "5")

    ContextDeconstructor[NestTest].deconstruct(NestTest("hi", Test(5)))
  }

  it should "work for sealed traits too" in {
    sealed trait Test
    case class TestFirst(thisIsX: Long) extends Test
    case class TestSecond(thisIsY: String) extends Test
    case class NestTest(notNested: String, nested: Test)

    val expectedFirst =
      Map[List[String], String](List("notNested") -> "hi",
                                List("nested", "thisIsX") -> "5")
    ContextDeconstructor[NestTest].deconstruct(NestTest("hi", TestFirst(5)))

    val expectedSecond =
      Map[List[String], String](List("notNested") -> "hi",
                                List("nested", "thisIsY") -> "yoBruv")
    ContextDeconstructor[NestTest].deconstruct(
      NestTest("hi", TestSecond("yoBruv")))
  }

  it should "work for recursive data structures" in {
    sealed trait Recursive
    case class RecursiveCase(r: Recursive) extends Recursive
    case object EndRecursion extends Recursive
    implicit val show: Show[EndRecursion.type] =
      Show.fromToString[EndRecursion.type]

    println(
      ContextDeconstructor[Recursive].deconstruct(
        RecursiveCase(RecursiveCase(RecursiveCase(EndRecursion)))))
  }
}

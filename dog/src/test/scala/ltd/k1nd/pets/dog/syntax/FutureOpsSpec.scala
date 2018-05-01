package ltd.k1nd.pets.dog.syntax

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import scala.concurrent.{ExecutionContext, Future}

class FutureOpsSpec extends FlatSpec with Matchers with MockFactory{
  "FutureOps" should "convert to IO without starting the future itself" in {
    implicit val mockCtx: ExecutionContext = mock[ExecutionContext]
    Future(println("On the side, I'm an effect.")).map("hey" + _).toIO
    (mockCtx.execute _).expects(*).never()
  }
}

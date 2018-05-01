package ltd.k1nd.pets.dog.syntax

import java.util.concurrent.atomic.AtomicBoolean
import cats.effect.IO
import org.scalatest.{Matchers, WordSpec}

class SyncOpsSpec extends WordSpec with Matchers {
  "delay" should{
    "suspend evaluation of a by name value" in {
      val bool = new AtomicBoolean(false)

      bool.get() should equal(false)

      val setBool = bool.set(true).delay[IO]

      bool.get() should equal(false)

      setBool.unsafeRunSync()

      bool.get() should equal(true)
    }
  }
}

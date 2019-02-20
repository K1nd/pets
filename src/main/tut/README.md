# pets

[![Latest version](https://index.scala-lang.org/k1nd/pets/pets/latest.svg)](https://index.scala-lang.org/k1nd/pets/pets) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/06b2e35106d847d994cff35bac230211)](https://app.codacy.com/app/wunderk1nd-e/pets?utm_source=github.com&utm_medium=referral&utm_content=K1nd/pets&utm_campaign=badger) [![CircleCI](https://circleci.com/gh/K1nd/pets.svg?style=shield)](https://circleci.com/gh/K1nd/pets) [![Coverage Status](https://coveralls.io/repos/github/K1nd/pets/badge.svg?branch=master)](https://coveralls.io/github/K1nd/pets?branch=master)

Helpers for cats &amp; co.


### Bones

Bones is a great cat as well as a pure tagless final wrapper for slf4j.
You should use Bones because:
* It allows you to separate out the orthogonal concern of carrying a logging context without relying on mutable global state
* It supports strongly typed logging contexts that are transformed into independent values in the `MDC`
    * e.g. `case class A(b: String)`, `case class C(d: A)` with `C(A("hi!"))` would be set in the `MDC` as `d.b -> "hi!"`
* It uses Li Haoyi's `sourcecode` to add the full name of the nearest enclosing definition where the logger was invoked to the MDC as the key `CodeLocation`

#### Example use
```tut:reset:silent
import cats.implicits._
import cats.mtl.implicits._
import cats.effect.IO
import cats.data.ReaderT
import ltd.k1nd.pets.bones.log.MdcLogger

case class Context(name: String, age: Int)
type MyReader[T] = ReaderT[IO, Context, T]
val logger = MdcLogger[IO, MyReader, Context](getClass).unsafeRunSync()
logger.info("This is a log - wow!").run(Context("Elias", 27)).unsafeRunSync()
```

### Dog

#### SyncOps
##### delay
Takes a by name value and lifts it into a Sync context.
```tut:reset
import ltd.k1nd.pets.dog.syntax.SyncOps._
import cats.effect.IO

val suspended = println("hey friend").delay[IO]

suspended.unsafeRunSync()
```

#### ContainerOps
##### liftEitherT
##### liftIntoOptionT
##### transform

#### BooleanOps
##### toApplicativeError
Converts a boolean into an applicative error, with true corresponding to success and false corresponding to error.
```tut:reset
import ltd.k1nd.pets.dog.syntax.BooleanOps._
import scala.util.Try
import cats.implicits.catsStdInstancesForTry

val exception: Throwable = new Exception("hey you")
val notUnit: Try[Unit] = false.toApplicativeError(exception)
val definitelyMyString: Try[String] = true.toApplicativeError(exception, "onSuccess")
```

##### ifElseThen
An extension method on boolean that evaluates to it's first argument if false and the second if true.
```tut:reset
import ltd.k1nd.pets.dog.syntax.BooleanOps._

val left = false.ifElseThen("left", "right")
val right = true.ifElseThen("left", "right")
```

##### toEither
An extension method on boolean that evaluates to left if false and right if true.
```tut:reset
import ltd.k1nd.pets.dog.syntax.BooleanOps._

val left = false.toEither("left", "right")
val right = true.toEither("left", "right")
```

##### whenA
An extension method on boolean that delegates to whenA - runs the provided effect when true, unit otherwise.

```tut:reset
import ltd.k1nd.pets.dog.syntax.BooleanOps._
import cats.Id

def runEffect(): Id[Unit] = println("I'm a side effect!")

val left = false.whenA(runEffect())
val right = true.whenA(runEffect())
```

#### FutureOps
##### toIO
Converts a Future to an IO without starting the Future
```tut:reset
import ltd.k1nd.pets.dog.syntax.FutureOps._
import scala.concurrent.Future

Future.successful(123).toIO
```

#### OptionOps
##### toOptionT
Transforms an option into an OptionT for some context (Applicative) F[_]
```tut:reset
import ltd.k1nd.pets.dog.syntax.OptionOps._
import cats.Id

Option(123).toOptionT[Id]
None.toOptionT[Id]
```

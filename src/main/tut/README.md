# pets

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/06b2e35106d847d994cff35bac230211)](https://app.codacy.com/app/wunderk1nd-e/pets?utm_source=github.com&utm_medium=referral&utm_content=K1nd/pets&utm_campaign=badger) [![CircleCI](https://circleci.com/gh/K1nd/pets.svg?style=shield)](https://circleci.com/gh/K1nd/pets) [![Coverage Status](https://coveralls.io/repos/github/K1nd/pets/badge.svg?branch=master)](https://coveralls.io/github/K1nd/pets?branch=master)

Helpers for cats &amp; co.

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


### Bones

#### SetOps
##### doesNotContain
This simply checks for the non-existance of an element in a set.
```tut:reset
import ltd.k1nd.pets.bones.syntax.SetOps._
Set(1,2,3).doesNotContain(4)
```
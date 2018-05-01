# pets

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/06b2e35106d847d994cff35bac230211)](https://app.codacy.com/app/wunderk1nd-e/pets?utm_source=github.com&utm_medium=referral&utm_content=K1nd/pets&utm_campaign=badger) [![CircleCI](https://circleci.com/gh/K1nd/pets.svg?style=shield)](https://circleci.com/gh/K1nd/pets) [![Coverage Status](https://coveralls.io/repos/github/K1nd/pets/badge.svg?branch=master)](https://coveralls.io/github/K1nd/pets?branch=master)

Helpers for cats &amp; co.

### Dog

#### SyncOps
##### delay
Takes a by name value and lifts it into a Sync context.
```scala
scala> import ltd.k1nd.pets.dog.syntax.SyncOps._
import ltd.k1nd.pets.dog.syntax.SyncOps._

scala> import cats.effect.IO
import cats.effect.IO

scala> val suspended = println("hey friend").delay[IO]
suspended: cats.effect.IO[Unit] = IO$158863444

scala> suspended.unsafeRunSync()
hey friend
```

#### ContainerOps
##### liftEitherT
##### liftIntoOptionT
##### transform

#### BooleanOps
##### toApplicativeError
Converts a boolean into an applicative error, with true corresponding to success and false corresponding to error.
```scala
scala> import ltd.k1nd.pets.dog.syntax.BooleanOps._
import ltd.k1nd.pets.dog.syntax.BooleanOps._

scala> import scala.util.Try
import scala.util.Try

scala> import cats.implicits.catsStdInstancesForTry
import cats.implicits.catsStdInstancesForTry

scala> val exception: Throwable = new Exception("hey you")
exception: Throwable = java.lang.Exception: hey you

scala> val notUnit: Try[Unit] = false.toApplicativeError(exception)
notUnit: scala.util.Try[Unit] = Failure(java.lang.Exception: hey you)

scala> val definitelyMyString: Try[String] = true.toApplicativeError(exception, "onSuccess")
definitelyMyString: scala.util.Try[String] = Success(onSuccess)
```

##### ifElseThen
An extension method on boolean that evaluates to it's first argument if false and the second if true.
```scala
scala> import ltd.k1nd.pets.dog.syntax.BooleanOps._
import ltd.k1nd.pets.dog.syntax.BooleanOps._

scala> val left = false.ifElseThen("left", "right")
left: String = left

scala> val right = true.ifElseThen("left", "right")
right: String = right
```

##### toEither
An extension method on boolean that evaluates to left if false and right if true.
```scala
scala> import ltd.k1nd.pets.dog.syntax.BooleanOps._
import ltd.k1nd.pets.dog.syntax.BooleanOps._

scala> val left = false.toEither("left", "right")
left: Either[String,String] = Left(left)

scala> val right = true.toEither("left", "right")
right: Either[String,String] = Right(right)
```

##### whenA
An extension method on boolean that delegates to whenA - runs the provided effect when true, unit otherwise.

```scala
scala> import ltd.k1nd.pets.dog.syntax.BooleanOps._
import ltd.k1nd.pets.dog.syntax.BooleanOps._

scala> import cats.Id
import cats.Id

scala> def runEffect(): Id[Unit] = println("I'm a side effect!")
runEffect: ()cats.Id[Unit]

scala> val left = false.whenA(runEffect())
left: cats.Id[Unit] = ()

scala> val right = true.whenA(runEffect())
I'm a side effect!
right: cats.Id[Unit] = ()
```

#### FutureOps
##### toIO
Converts a Future to an IO without starting the Future
```scala
scala> import ltd.k1nd.pets.dog.syntax.FutureOps._
import ltd.k1nd.pets.dog.syntax.FutureOps._

scala> import scala.concurrent.Future
import scala.concurrent.Future

scala> Future.successful(123).toIO
res0: cats.effect.IO[Int] = IO$1643403756
```

#### OptionOps
##### toOptionT
Transforms an option into an OptionT for some context (Applicative) F[_]
```scala
scala> import ltd.k1nd.pets.dog.syntax.OptionOps._
import ltd.k1nd.pets.dog.syntax.OptionOps._

scala> import cats.Id
import cats.Id

scala> Option(123).toOptionT[Id]
res0: cats.data.OptionT[cats.Id,Int] = OptionT(Some(123))

scala> None.toOptionT[Id]
res1: cats.data.OptionT[cats.Id,Nothing] = OptionT(None)
```


### Bones

#### SetOps
##### doesNotContain
This simply checks for the non-existance of an element in a set.
```scala
scala> import ltd.k1nd.pets.bones.syntax.SetOps._
import ltd.k1nd.pets.bones.syntax.SetOps._

scala> Set(1,2,3).doesNotContain(4)
res0: Boolean = true
```

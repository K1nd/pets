# pets

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/06b2e35106d847d994cff35bac230211)](https://app.codacy.com/app/wunderk1nd-e/pets?utm_source=github.com&utm_medium=referral&utm_content=K1nd/pets&utm_campaign=badger) [![CircleCI](https://circleci.com/gh/K1nd/pets.svg?style=shield)](https://circleci.com/gh/K1nd/pets)

Helpers for cats &amp; co.

###Dog

####BooleanOps


####ContainerOps


####FutureOps


####LiftOps


####OptionOps
#####toOptionT
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


###Bones

####SetOps
#####doesNotContain
This simply checks for the non-existance of an element in a set.
```scala
scala> import ltd.k1nd.pets.bones.syntax.SetOps._
import ltd.k1nd.pets.bones.syntax.SetOps._

scala> Set(1,2,3).doesNotContain(4)
res0: Boolean = true
```

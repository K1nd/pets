# pets

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/06b2e35106d847d994cff35bac230211)](https://app.codacy.com/app/wunderk1nd-e/pets?utm_source=github.com&utm_medium=referral&utm_content=K1nd/pets&utm_campaign=badger) [![CircleCI](https://circleci.com/gh/K1nd/pets.svg?style=shield)](https://circleci.com/gh/K1nd/pets) [![Coverage Status](https://coveralls.io/repos/github/K1nd/pets/badge.svg?branch=tidying-build)](https://coveralls.io/github/K1nd/pets?branch=tidying-build)

Helpers for cats &amp; co.

### Dog

#### BooleanOps


#### ContainerOps


#### FutureOps


#### LiftOps


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
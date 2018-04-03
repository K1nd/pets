package ltd.k1nd

import ltd.k1nd.pets.bones.syntax.{AllSyntax => BonesSyntax}
import ltd.k1nd.pets.dog.syntax.{AllSyntax => DogSyntax}

package object pets extends BonesSyntax with DogSyntax
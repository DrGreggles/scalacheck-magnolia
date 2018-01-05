# scalacheck-magnolia
Generation of arbitrary case classes / ADTs instances with scalacheck and magnolia

Inspired by 
-  ScalaCheck-Shapeless https://github.com/alexarchambault/scalacheck-shapeless/
-  Magnolia http://magnolia.work/

It should be quite a lot faster than shapeless.

To use, add the following imports where required:
```
import ArbitraryDerivation.arbGen
import MagShrinkDerivation.{shrGen, toShrink}
import CogenDerivation.cogenGen
```

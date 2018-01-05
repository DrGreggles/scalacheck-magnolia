package org.scalacheck

import scala.language.experimental.macros

import magnolia._
import org.scalacheck.rng.Seed

object CogenDerivation {
  type Typeclass[T] = Cogen[T]

  def combine[T](caseClass: CaseClass[Cogen, T]): Cogen[T] = Cogen { item: T =>
    item.hashCode.toLong
  }

  def dispatch[T](sealedTrait: SealedTrait[Cogen, T]): Cogen[T] = Cogen { (seed: Seed, item: T) =>
    sealedTrait.dispatch(item) { subtype =>
      subtype.typeclass.perturb(seed, subtype.cast(item))
    }
  }

  implicit def cogenGen[T]: Cogen[T] = macro Magnolia.gen[T]
}
package org.scalacheck

import scala.language.experimental.macros

import magnolia._

object MagShrinkDerivation {
  type Typeclass[T] = MagShrink[T]

  def combine[T](caseClass: CaseClass[MagShrink, T]): MagShrink[T] = MagShrink { item: T =>
    for {
      shrinkParam <- caseClass.parameters.toStream
      shrunkenParam <- shrinkParam.typeclass.shrink(shrinkParam.dereference(item))
    } yield caseClass.construct {
      case param if param.label == shrinkParam.label => shrunkenParam
      case param => param.dereference(item)
    }
  }

  def dispatch[T](sealedTrait: SealedTrait[MagShrink, T]): MagShrink[T] = MagShrink { item: T =>
    sealedTrait.dispatch(item) { subtype =>
      subtype.typeclass.shrink(subtype.cast(item))
    }
  }

  implicit def shrGen[T]: MagShrink[T] = macro Magnolia.gen[T]

  implicit def toShrink[T](implicit magShrink: MagShrink[T]) = Shrink(magShrink.shrink(_))
}
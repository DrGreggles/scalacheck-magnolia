package org.scalacheck

import scala.language.experimental.macros

import magnolia._
import org.scalacheck.rng.Seed

trait ArbitraryDerivation {
  type Typeclass[T] = Arbitrary[T]

  /** Magnolia only provides caseClass.construct which takes a dependently typed value for each parameter of caseClass.
    * This means that we cannot flatMap all of the generators for each parameter and construct the case class as would be standard.
    * E.g.
    *   if we have a case class Hello(s: String, n: Int)
    *   We would usually take a Gen[String] and Gen[Int], and flatMap them to make a Gen[Hello]
    *
    *   This doesn't seem to be possible with Magnolia, since the dependent types required only appear within the application of caseClass.construct
    *
    *   So we must construct Gen[Hello] using the internal ScalaCheck methods, to build the case class element by element.
    *
    */
  def combine[T](caseClass: CaseClass[Arbitrary, T]): Arbitrary[T] = Arbitrary {
    Gen.gen { (p, seed0) =>

      def seeds: Stream[Seed] = Stream.iterate(seed0)(_.next)

      val seedMap = caseClass.parameters.zipWithIndex.map { case (param, n) => param.label -> seeds(n) }.toMap

      def construct = try {
        Some(caseClass.construct(param =>
          param.typeclass.arbitrary.pureApply(p, seedMap(param.label))
        ))
      } catch {
        case e: NoSuchElementException => None
      }

      Gen.r(construct, seeds(caseClass.parameters.length))
    }
  }

  def dispatch[T](sealedTrait: SealedTrait[Arbitrary, T]): Arbitrary[T] = {

    val gen: Gen[T] = for {
      n <- Gen.choose(0, sealedTrait.subtypes.length - 1)
      sub <- sealedTrait.subtypes(n).typeclass.arbitrary
    } yield sub

    Arbitrary(gen)
  }

  implicit def arbGen[T]: Arbitrary[T] = macro Magnolia.gen[T]
}

object ArbitraryDerivation extends ArbitraryDerivation
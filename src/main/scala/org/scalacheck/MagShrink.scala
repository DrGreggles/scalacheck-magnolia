package org.scalacheck

import scala.collection.Traversable
import scala.language.higherKinds

import org.scalacheck.util.Buildable

/** Duplicate of Shrink[T] for Magnolia.
  * Required because otherwise the default shrink (shrinkAny[T]) is used instead of magnolia generated Shrink[T]s in recursive magnolia calls */
sealed abstract class MagShrink[T] {
  def shrink(x: T): Stream[T]
}

object MagShrink {
  def apply[T](f: T => Stream[T]): MagShrink[T] = new MagShrink[T] {
    override def shrink(x: T) = f(x)
  }

  // annoyingly we need to manually pull in default instances of shrink, to avoid shrinkAny
  def fromShrink[T](implicit shrink: Shrink[T]) = MagShrink(shrink.shrink(_))
  implicit lazy val intInstance = fromShrink[Int]
  implicit lazy val shrinkString = fromShrink[String]
  implicit def shrinkContainer[C[_], T](implicit v: C[T] => Traversable[T], s: Shrink[T], b: Buildable[T, C[T]]) = fromShrink[C[T]]
  implicit def shrinkContainer2[C[_, _], T, U](implicit v: C[T, U] => Traversable[(T, U)], s: Shrink[(T, U)], b: Buildable[(T, U), C[T, U]]) = fromShrink[C[T, U]]
  implicit def shrinkFractional[T: Fractional] = fromShrink[T]
  implicit def shrinkIntegral[T: Integral] = fromShrink[T]
  implicit def shrinkOption[T: Shrink] = fromShrink[Option[T]]
  implicit def shrinkTuple2[T1: Shrink, T2: Shrink] = fromShrink[(T1, T2)]
  implicit def shrinkTuple3[T1: Shrink, T2: Shrink, T3: Shrink] = fromShrink[(T1, T2, T3)]
  implicit def shrinkTuple4[T1: Shrink, T2: Shrink, T3: Shrink, T4: Shrink] = fromShrink[(T1, T2, T3, T4)]
  implicit def shrinkTuple5[T1: Shrink, T2: Shrink, T3: Shrink, T4: Shrink, T5: Shrink] = fromShrink[(T1, T2, T3, T4, T5)]
  implicit def shrinkTuple6[T1: Shrink, T2: Shrink, T3: Shrink, T4: Shrink, T5: Shrink, T6: Shrink] = fromShrink[(T1, T2, T3, T4, T5, T6)]
  implicit def shrinkTuple7[T1: Shrink, T2: Shrink, T3: Shrink, T4: Shrink, T5: Shrink, T6: Shrink, T7: Shrink] = fromShrink[(T1, T2, T3, T4, T5, T6, T7)]
  implicit def shrinkTuple8[T1: Shrink, T2: Shrink, T3: Shrink, T4: Shrink, T5: Shrink, T6: Shrink, T7: Shrink, T8: Shrink] = fromShrink[(T1, T2, T3, T4, T5, T6, T7, T8)]
  implicit def shrinkTuple9[T1: Shrink, T2: Shrink, T3: Shrink, T4: Shrink, T5: Shrink, T6: Shrink, T7: Shrink, T8: Shrink, T9: Shrink] = fromShrink[(T1, T2, T3, T4, T5, T6, T7, T8, T9)]
  implicit def shrinkEither[T1: Shrink, T2: Shrink] = fromShrink[Either[T1, T2]]

}
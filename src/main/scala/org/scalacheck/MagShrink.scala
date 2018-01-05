package org.scalacheck

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
  implicit val intInstance = fromShrink[Int]
  implicit val stringInstance = fromShrink[String]
}
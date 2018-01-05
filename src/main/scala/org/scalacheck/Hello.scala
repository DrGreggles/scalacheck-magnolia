package org.scalacheck



object Hello extends App {

  import ArbitraryDerivation.arbGen
  import MagShrinkDerivation.{shrGen, toShrink}
  import CogenDerivation.cogenGen

  case class F(x: String) extends Letter
  case class G(x: String, y: Int) extends Letter

  val g = implicitly[Arbitrary[F]]

  println(g.arbitrary.sample)
  println(g.arbitrary.sample)
  println(g.arbitrary.sample)
  println(g.arbitrary.sample)
  println(g.arbitrary.sample)
  println(g.arbitrary.sample)
  println(g.arbitrary.sample)
  println(g.arbitrary.sample)

  sealed trait Letter
  case object A extends Letter
  case object B extends Letter
  case object C extends Letter
  case object D extends Letter
  case object E extends Letter

  val letGen = implicitly[Arbitrary[Letter]]

  println(letGen.arbitrary.sample)
  println(letGen.arbitrary.sample)
  println(letGen.arbitrary.sample)
  println(letGen.arbitrary.sample)
  println(letGen.arbitrary.sample)
  println(letGen.arbitrary.sample)


  println(implicitly[MagShrink[Letter]].shrink(F("skdfjskdfjksdjf")).take(10).toList)

  println(implicitly[MagShrink[String]].shrink("ajksdkasjdk").take(10).toList)


  implicitly[Shrink[Letter]]
  implicitly[MagShrink[Letter]]
  //  implicit val asd = MagShrink.toShrink[Letter]

  println("%%%%%%%%%%%%%%%%")
  println(implicitly[Shrink[Letter]].shrink(F("skdfjskdfjksdjf")).take(10).toList)


  //    println(MagShrinkDerivation.gen[G].shrink(G("askjdhk", 1000)))
  //    println(s"G:${MagShrinkDerivation.gen[Letter].shrink(G("askjdhk", 1000))}")
  //    println(s"F:${MagShrinkDerivation.gen[Letter].shrink(F("askjdhk"))}")
  //    println(s"A:${MagShrinkDerivation.gen[Letter].shrink(A)}")


}
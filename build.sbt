name := "scalacheck-magnolia"

version := "0.0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.13.4",
  "com.propensive" %% "magnolia" % "0.6.1",
  "com.chuusai" %% "shapeless" % "2.3.3"
)
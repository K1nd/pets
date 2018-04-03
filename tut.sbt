enablePlugins(TutPlugin)

tutTargetDirectory := baseDirectory.value

scalacOptions in Tut := Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-target:jvm-1.8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps"
)

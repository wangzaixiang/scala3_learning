val scala3Version = "3.0.0-RC3"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",

    scalaVersion := scala3Version,
    //scalacOptions := Seq("-language:strictEquality") ,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
      libraryDependencies += "org.scala-lang" % "scala3-compiler_3.0.0-RC3" % scala3Version % "compile"
  )

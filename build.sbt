import sbt.Keys.libraryDependencies

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scala-starter-example""",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2" % "1.4.199",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    ),
      libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.3.2",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
      "org.postgresql" % "postgresql" % "42.2.5",
      "org.elasticsearch.client" % "elasticsearch-rest-high-level-client" % "6.2.4",
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.8.0"
    )
  )

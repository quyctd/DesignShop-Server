name := """DuAn2018"""

version := "1.0-SNAPSHOT"
import com.typesafe.sbt.SbtGit._

lazy val commonSettings = Seq(
  scalaVersion := "2.12.2",
  herokuAppName in Compile := "design-tool-shop",
  libraryDependencies ++= Seq(
    guice,
    jdbc,
    evolutions,
    "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.0" % Test,
    "mysql" % "mysql-connector-java" % "5.1.44",
    "org.scalikejdbc" %% "scalikejdbc" % "3.2.2",
    "org.scalikejdbc" %% "scalikejdbc-config" % "3.2.2",
    "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.6.0-scalikejdbc-3.2",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    specs2 % Test,
    "org.mindrot" % "jbcrypt" % "0.3m",
    "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
    "javax.xml.bind" % "jaxb-api" % "2.1" ,
    "org.scalikejdbc" %% "scalikejdbc-joda-time" % "3.3.2"
  )
)

lazy val root = (project in file("."))
  .aggregate(application, domain, infra)
  .settings(
    run := {
      (run in application in Compile).evaluated
    }
  )

lazy val application = (project in file("application"))
  .dependsOn(domain, infra)
  .enablePlugins(PlayScala)
  .settings(commonSettings)
  .settings(
    routesGenerator := InjectedRoutesGenerator,
    parallelExecution in Test := false
  )

lazy val domain = (project in file("domain"))
  .dependsOn(infra)
  .settings(commonSettings)
  .settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    parallelExecution in Test := false
  )

lazy val infra = (project in file("infra"))
  .settings(commonSettings)
  .settings(
    scalaSource in Compile := baseDirectory.value / "src" / "main" / "scala",
    scalaSource in Test := baseDirectory.value / "src" / "test" / "scala",
    resourceDirectory in Test := baseDirectory.value / "src" / "test" / "resources",
    parallelExecution in Test := false
  )

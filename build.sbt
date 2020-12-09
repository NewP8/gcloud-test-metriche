import com.lightbend.lagom.core.LagomVersion

organization in ThisBuild := "org.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.13.0"

lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val akkaDiscoveryKubernetesApi =
  "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % "1.0.1"
val lagomScaladslAkkaDiscovery =
  "com.lightbend.lagom" %% "lagom-scaladsl-akka-discovery-service-locator" % LagomVersion.current

lazy val `hello` = (project in file("."))
  .aggregate(`hello-api`, `hello-impl`)

lazy val `hello-api` = (project in file("hello-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `hello-impl` = (project in file("hello-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      lagomScaladslAkkaDiscovery,
      akkaDiscoveryKubernetesApi
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-api`)

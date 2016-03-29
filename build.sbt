organization in ThisBuild := "com.dslplatform.examples"
name in ThisBuild         := "lagom-postgres"
version in ThisBuild      := "0.0.1"

lazy val dslJsonLagom = project("dsl-json-lagom") settings(
  libraryDependencies ++= Seq(
    lagomJavadslApi
  , "org.revenj" % "revenj-core" % "0.9.4"
  )
)

lazy val storageApi = apiProject("storage")
lazy val storageImpl = implProject("storage") dependsOn(storageApi)

lazy val wondersApi = apiProject("wonders")
lazy val wondersImpl = implProject("wonders") dependsOn(wondersApi)

lazy val commentsApi = apiProject("comments")
lazy val commentsImpl = implProject("comments") dependsOn(commentsApi, wondersApi)

def apiProject(id: String) = project(id + "-api") settings(
  unmanagedJars in Compile += baseDirectory.value / "model-lib" / (id + "-api-model.jar")
) dependsOn(dslJsonLagom)


def implProject(id: String) = project(id + "-impl") settings(
  unmanagedJars in Compile += baseDirectory.value / "model-lib" / (id + "-impl-model.jar")
, dependencyClasspath in Compile := (dependencyClasspath in Compile).value filterNot {
    _.data.getName == (id + "-api-model.jar")
  }
, dependencyClasspath in Runtime := (dependencyClasspath in Runtime).value filterNot {
    _.data.getName == (id + "-api-model.jar")
  }
) enablePlugins(LagomJava)

def project(id: String) = (Project(id, base = file(id))
  settings(
    unmanagedSourceDirectories in Compile := Seq((javaSource in Compile).value)
  , unmanagedSourceDirectories in Test := Seq((javaSource in Test).value)
  , EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
  , EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE18)
  , EclipseKeys.withBundledScalaContainers := false
  , EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
  , EclipseKeys.eclipseOutput := Some(".target")
  , EclipseKeys.withSource := true
  , EclipseKeys.withJavadoc := true
  )
)

lazy val frontEnd = project("front-end") settings(
  libraryDependencies ++= Seq(
    "org.webjars" % "font-awesome" % "4.5.0"
  , "org.webjars" % "isotope" % "2.2.2"
  )
, routesGenerator := InjectedRoutesGenerator
, TwirlKeys.templateImports ++= Seq(
    "worldwonders._"
  )
) enablePlugins(
    PlayJava
  , LagomPlay
) dependsOn(storageApi, commentsApi, wondersApi)

lagomCassandraEnabled in ThisBuild := false

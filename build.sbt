organization in ThisBuild := "com.dslplatform.examples"
name in ThisBuild         := "lagom-postgres"
version in ThisBuild      := "0.0.1"

lazy val storageApi = apiProject("storage")
lazy val storageImpl = implProject("storage") dependsOn(storageApi)

lazy val wondersApi = apiProject("wonders")
lazy val wondersImpl = implProject("wonders") dependsOn(wondersApi, storageApi)

lazy val commentsApi = apiProject("comments")
lazy val commentsImpl = implProject("comments") dependsOn(commentsApi, wondersApi)

def apiProject(id: String) = project(id + "-api"
, lagomJavadslApi
, "org.revenj" % "revenj-core" % "0.9.3"
)

def implProject(id: String) = project(id + "-impl") enablePlugins(LagomJava)

def project(id: String, dependencies: ModuleID*) = (Project(id, base = file(id))
  settings(
    libraryDependencies ++= dependencies
  , unmanagedSourceDirectories in Compile := Seq(
      (javaSource in Compile).value
    , sourceDirectory.value / "generated" / "java"
    )
  , unmanagedSourceDirectories in Test := Seq((javaSource in Compile).value)
  , EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
  , EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE18)
  , EclipseKeys.withBundledScalaContainers := false
  , EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
  , EclipseKeys.eclipseOutput := Some(".target")
  , EclipseKeys.withSource := true
  , EclipseKeys.withJavadoc := true
  )
)

lazy val frontEnd = (project("front-end"
, "org.webjars" % "jquery" % "2.2.1"
, "org.webjars" % "bootstrap" % "3.3.6"
, "org.webjars" % "bootstrap-switch" % "3.3.2"
) enablePlugins(
    PlayJava
  , LagomPlay
) settings(routesGenerator := InjectedRoutesGenerator)
) dependsOn(commentsApi, wondersApi)

lagomCassandraEnabled in ThisBuild := false

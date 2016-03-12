organization in ThisBuild := "com.mentatlabs.examples"
name in ThisBuild         := "lagom-postgres"
version in ThisBuild      := "0.0.1"

lazy val fooApi = (project
  settings(
    libraryDependencies += lagomJavadslApi
  )
)

lazy val fooImpl = (project
  enablePlugins(LagomJava)
  dependsOn(fooApi)
)

lagomCassandraEnabled in ThisBuild := false

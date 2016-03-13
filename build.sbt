organization in ThisBuild := "com.mentatlabs.examples"
name in ThisBuild         := "lagom-postgres"
version in ThisBuild      := "0.0.1"

lazy val guestApi = (project
  settings(
    libraryDependencies += lagomJavadslApi
  )
)

lazy val guestImpl = (project
  enablePlugins(LagomJava)
  dependsOn(guestApi)
)

lagomCassandraEnabled in ThisBuild := false

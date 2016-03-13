organization in ThisBuild := "com.dslplatform.examples"
name in ThisBuild         := "lagom-postgres"
version in ThisBuild      := "0.0.1"

lazy val domainModel = (project
  settings(
    libraryDependencies ++= Seq(
      "org.revenj" % "revenj-core" % "0.9.2"
    )
  )
)

lazy val guestApi = (project
  settings(
    libraryDependencies += lagomJavadslApi
  )
) dependsOn(domainModel)

lazy val guestImpl = (project
  enablePlugins(LagomJava)
  dependsOn(guestApi)
)

lazy val adminApi = (project
  settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi
    , "commons-io" % "commons-io" % "2.4"
    )
  )
) dependsOn(domainModel)

lazy val adminImpl = (project
  enablePlugins(LagomJava)
  dependsOn(adminApi)
)

lagomCassandraEnabled in ThisBuild := false

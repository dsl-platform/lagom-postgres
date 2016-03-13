organization in ThisBuild := "com.dslplatform.examples"
name in ThisBuild         := "lagom-postgres"
version in ThisBuild      := "0.0.1"

lazy val domainModel = (project
  settings(
    libraryDependencies ++= Seq(
      "org.revenj" % "revenj-core" % "0.9.3"
    )
  , unmanagedSourceDirectories in Compile := Seq(sourceDirectory.value / "generated" / "java")
  )
)

lazy val guestApi = (project
  settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi
    )
  )
) dependsOn(domainModel)

lazy val guestImpl = (project
  enablePlugins(LagomJava)
) dependsOn(guestApi)

lazy val adminApi = (project
  settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi
    )
  )
) dependsOn(domainModel)

lazy val adminImpl = (project
  enablePlugins(LagomJava)
) dependsOn(adminApi)

lagomCassandraEnabled in ThisBuild := false

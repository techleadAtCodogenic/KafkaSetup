val kafkaService = ServiceBuilder()
  .addMaster()
  .addWorker()
  .addWorker()
  .asHdfsCluster()
  .configurableAttributes(2, 1)

val build = SBuild("com.codogenic", "kafka-cluster", "0.0.1")
  .sourceDirectories("src", "kclients")
  .dependencies(docker_core(), docker_definitions(), "org.apache.kafka" % "kafka-clients" % "3.1.0")
  .testSourceDirectories("spec")
  .testDependencies( scalatest() )
  .services(kafkaService)
  .scalaVersions("2.13.7")

packMain := Map("listtopics" -> "com.codogenic.kclients.ListTopics")

val kafkaClusterProject = (project in file("."))
  .settings( build.settings )
  .enablePlugins(FluentStyleSbt, PackPlugin)

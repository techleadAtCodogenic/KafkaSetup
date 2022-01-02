val clusterService = ServiceBuilder()
  .addMaster()
  .addWorker()
  .addWorker()
  .asKafkaCluster()
  .serviceTimeouts(2, 1)

val build = SBuild("com.codogenic", "kafka-cluster", "0.0.1")
  .sourceDirectories("src")
  .dependencies(docker_core(), docker_definitions())
  .services(clusterService)
  .moduleScalaVersion("2.13.7")

val kafkaClusterProject = (project in file("."))
  .settings( build.settings )
  .enablePlugins(FluentStyleSbt)

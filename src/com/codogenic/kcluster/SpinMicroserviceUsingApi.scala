package com.codogenic.kcluster

import com.logicovercode.docker.cluster.ClusterBuilderDefinitions
import com.logicovercode.docker.kafka.ClusterNodeKafkaExtension
import com.logicovercode.wdocker.DockerNetwork
import com.logicovercode.wdocker.OsFunctions.currentOsOption
import com.logicovercode.wdocker.api.{DockerContext, DockerProcessFunctions, DockerSystem}

import scala.concurrent.duration.DurationInt

object SpinMicroserviceUsingApi extends ClusterNodeKafkaExtension with ClusterBuilderDefinitions{

  val hnet = DockerNetwork("hnet", Option("192.168.33.0/16"))
  val container = ServiceBuilder(hnet).addMaster().asKafkaCluster().masterNode.kafkaNodeDefinition()

  def main(args: Array[String]): Unit = {

    println("start of main")

    println("starting service >" + container + "<")

    DockerSystem.tryNetworkConnectivity(hnet)(DockerContext.dockerClient)
    DockerSystem.pullAndStartContainerDefinition(container, 1.minute, 1.minute)(DockerContext.dockerClient, DockerContext.dockerFactory)


    println("service started >" + container + "<")

    val processId = DockerProcessFunctions.pid()
    DockerProcessFunctions.killDockerManager(processId, currentOsOption)

    println("end of main")
  }
}

import akka.actor.{Props, ActorSystem}
import akka.cluster.Cluster

/**
  * Created by yjtsai on 2016/3/2.
  */
object ChatRoom {
  def main(args : Array[String]): Unit = {
    val systemName = "ChatRoom"
    val system = ActorSystem(systemName)
    val clusterAddress = Cluster(system).selfAddress
    Cluster(system).join(clusterAddress)
    val c1 = system.actorOf(Props(classOf[ChatUser], "room1"), "Bob")
    Thread.sleep(1000)
    val c2 = system.actorOf(Props(classOf[ChatUser], "room1"), "Tom")

    c1 ! ChatUser.Send("Hello!")

    c2 ! ChatUser.Send("Hi! Guys!")

    Thread.sleep(2000)

    val system2 = ActorSystem(systemName + "2")
    val clusterAddress2 = Cluster(system2).selfAddress
    Cluster(system2).join(clusterAddress2)
    val c3 = system2.actorOf(Props(classOf[ChatUser], "room2"), "Mary")
    Thread.sleep(1000)
    val c4 = system2.actorOf(Props(classOf[ChatUser], "room1"), "Cathy")

    c4 ! ChatUser.Send("I am Cathy!")


  }
}


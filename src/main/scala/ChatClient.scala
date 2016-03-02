import akka.actor.{Actor, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Publish, Subscribe}

/**
  * Created by yjtsai on 2016/3/2.
  */
object ChatClient {
  case class Message(from : String, msg : String)
  case class Info(from : String, msg : String)

  case class Connect(user : String)
  case class Disconnect(user : String)
  case class Send(msg : String)

  def props(room : String, name : String) = Props(classOf[ChatClient], room, name)
}

class ChatClient(val room : String, name : String) extends Actor {
  val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe(room, self)

  def receive = {
    case ChatClient.Send(msg) => {
      mediator ! Publish(room, ChatClient.Message(name, msg))
    }
    case ChatClient.Connect(user) => {
      mediator ! Publish(room, ChatClient.Info(name, s" $user 加入了 $room"))
    }
    case ChatClient.Disconnect(user) => {
      mediator ! Publish(room, ChatClient.Info(name, s" $user 離開了 $room"))
    }
    case ChatClient.Message(user, msg) => {
      val from = if(sender == self) "你 發送：" else s"$user 發送："
      // 這邊要透過 websocket 轉發
      println(s"[$room][$name's view] $from $msg")
    }
    case ChatClient.Info(user, msg) => {
      // 這邊要透過 websocket 轉發
      println(s"[$room][$name's view] 系統發送： $msg")
    }
  }
}

import akka.actor.Actor

/**
  * Created by yjtsai on 2016/3/2.
  */
object ChatUser {
  case class Send(msg : String)
}

class ChatUser(val room : String) extends Actor {
  val user = context.actorOf(ChatClient.props(room, self.path.name), "client")

  override def preStart() : Unit = {
    val name = self.path.name
    user ! ChatClient.Connect(name)
  }

  def receive = {
    case ChatUser.Send(msg) => {
      user ! ChatClient.Send(msg)
    }
  }
}

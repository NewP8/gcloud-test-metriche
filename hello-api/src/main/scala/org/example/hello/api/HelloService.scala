package org.example.hello.api

import akka.Done
import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Descriptor
import com.lightbend.lagom.scaladsl.api.Service
import com.lightbend.lagom.scaladsl.api.ServiceCall
import play.api.libs.json.Format
import play.api.libs.json.Json

object HelloService {
  val TOPIC_NAME = "greetings"
}

trait HelloService extends Service {

  def addItem(): ServiceCall[ItemT, Done]
  def getItem(id: Int): ServiceCall[NotUsed, Done]
  def deleteItem(id: Int): ServiceCall[NotUsed, Done]

  override final def descriptor: Descriptor = {
    import Service._
    import com.lightbend.lagom.scaladsl.api.transport.Method

    named("orders")
      .withCalls(
        restCall(Method.POST, "/item", addItem _),
        restCall(Method.GET, "/item/:itemId", getItem _),
        restCall(Method.DELETE, "/item/:itemId", deleteItem _)
      )
      .withAutoAcl(true)
  }
}

case class ItemT(id: Int, message: Option[String])

object ItemT {
  implicit val format: Format[ItemT] = Json.format[ItemT]
}

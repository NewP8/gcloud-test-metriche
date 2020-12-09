package org.example.hello.impl

import akka.Done
import akka.NotUsed
import akka.util.Timeout
import com.lightbend.lagom.scaladsl.api.ServiceCall
import org.example.hello.api.HelloService
import org.example.hello.api.ItemT

import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * Implementation of the HelloService.
  */
class HelloServiceImpl()(implicit ec: ExecutionContext) extends HelloService {

  implicit val timeout = Timeout(5.seconds)

  override def addItem(): ServiceCall[ItemT, Done] =
    ServiceCall { item =>
      Future.successful(Done)
    }

  override def getItem(id: Int): ServiceCall[NotUsed, Done] =
    ServiceCall { _ =>
      if (id % 2 == 0)
        Future.successful(Done)
      else
        throw new NoSuchElementException()
    }

  override def deleteItem(id: Int): ServiceCall[NotUsed, Done] =
    ServiceCall { item =>
      Future.successful(Done)
    }
}

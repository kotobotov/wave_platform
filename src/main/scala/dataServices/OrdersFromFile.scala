package dataServices
import config.Files
import model._

import scala.io.Source

/**
 * Created by Kotobotov.ru on 14.02.2019.
 */
class OrdersFromFile extends OrderService {
  private def parse(str: String): Order = {
    str.split("\t") match {
      case Array(client, direction, ticket, price, count) =>
        Order(OrderService.increasedId, client, if (direction == "b") Buy else Sell, ticket, price.toInt, count.toInt)
    }
  }
  private val inputData = Source.fromFile(Files.ORDERS).getLines
  override def next: Order = parse(inputData.next())
  override def isEmpty: Boolean = inputData.isEmpty
}

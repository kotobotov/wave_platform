package config

/**
  * Created by Kotobotov.ru on 14.02.2019.
  */
object Files {
  val ORDERS_LIMIT = 1000000.toDouble // участвует в контроле очередности исполнения заявок с одинаковой ценой
  val CLIENTS = "resource/clients.txt"
  val ORDERS = "resource/orders.txt"
  val RESULT = "resource/result.txt"
}

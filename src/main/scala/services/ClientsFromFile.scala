package services
import config.Files
import model.Client

import scala.io.Source

/**
  * Created by Kotobotov.ru on 14.02.2019.
  */
class ClientsFromFile extends ClientService {
  private val inputData = Source.fromFile(Files.CLIENTS).getLines
  private def parse(input: String): Client = {
    input.split("\t") match {
      case Array(clientId, dollars, a, b, c, d) =>
        val currentClient = Client(clientId)
        currentClient.balance = dollars.toInt
        currentClient.stocks("A") = a.toInt
        currentClient.stocks("B") = b.toInt
        currentClient.stocks("C") = c.toInt
        currentClient.stocks("D") = d.toInt
        currentClient
    }
  }

  override def next = parse(inputData.next())
  override def isEmpty: Boolean = inputData.isEmpty
}

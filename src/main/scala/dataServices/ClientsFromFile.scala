package dataServices
import model.Client
import config.Files
import scala.io.Source

/**
 * Created by Kotobotov.ru on 14.02.2019.
 */
class ClientsFromFile extends ClientService {
 val inputData = Source.fromFile(Files.CLIENTS).getLines
  private def parse(input: String): Client = {
    input.split("\t") match {
      case Array(clientId, dollars, a, b, c, d) =>
        Client(clientId,  dollars.toInt, Map("A" -> a.toInt, "B" -> b.toInt, "C" -> c.toInt, "D" -> d.toInt))
    }
  }

  override def next = parse(inputData.next())
  override def isEmpty: Boolean = inputData.isEmpty
}

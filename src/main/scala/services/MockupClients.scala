package services
import model.Client

/**
  * Created by Kotobotov.ru on 14.02.2019.
  */
class MockupClients extends ClientService {
  private val inputData = List(
    "C1\t1000\t130\t240\t760\t320",
    "C2\t4350\t370\t120\t950\t560",
    "C3\t2760\t0\t0\t0\t0",
    "C4\t560\t450\t540\t480\t950",
    "C5\t1500\t0\t0\t400\t100",
    "C6\t1300\t890\t320\t100\t0",
    "C7\t750\t20\t0\t790\t0",
    "C8\t7000\t90\t190\t0\t0",
    "C9\t7250\t190\t190\t0\t280"
  ).toIterator

  // в мокапе копия парсера что и в КлиентСервисе НО не факт что вообще этот парсер в заглушке будет, и что этот парсер будет именно в таком виде
  // поэтому это отдельная независимая копия, а не переиспользование
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

  override def next: Client = parse(inputData.next())
  override def isEmpty: Boolean = inputData.isEmpty
}

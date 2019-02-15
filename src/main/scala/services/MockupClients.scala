package services
import model.Client

/**
 * Created by Kotobotov.ru on 14.02.2019.
 */
class MockupClients  extends ClientService {
  private val inputData = List(Client("C1",1000,Map("A" -> 130, "B"-> 240, "C" -> 760, "D" -> 320)),
  Client("C2",4350,Map("A" -> 370, "B" -> 120, "C" -> 950, "D" -> 560)),
  Client("C3",2760,Map("A" -> 0, "B" -> 0, "C" -> 0, "D" -> 0)),
  Client("C4",560,Map("A" -> 450, "B" -> 540, "C" -> 480, "D" -> 950)),
  Client("C5",1500,Map("A" -> 0, "B" -> 0, "C" -> 400, "D" -> 100)),
  Client("C6",1300,Map("A" -> 890, "B" -> 320, "C" -> 100, "D" -> 0)),
  Client("C7",750,Map("A" -> 20, "B" -> 0, "C" -> 790, "D" -> 0)),
  Client("C8",7000,Map("A" -> 90, "B" -> 190, "C" -> 0, "D" -> 0)),
  Client("C9",7250,Map("A" -> 190, "B" -> 190, "C" -> 0, "D" -> 280))
  ).toIterator
  private def parse(input: String): Client = {
    println(input)
    input.split("\t") match {
      case Array(clientId, dollars, a, b, c, d) =>
        Client(clientId,  dollars.toInt, Map("A" -> a.toInt, "B" -> b.toInt, "C" -> c.toInt, "D" -> d.toInt))
    }
  }

  override def next: Client = inputData.next()
  override def isEmpty: Boolean = inputData.isEmpty
}

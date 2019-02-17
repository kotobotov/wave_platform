import org.scalatest.FreeSpec
import services._

/**
  * Created by Kotobotov.ru on 13.02.2019.
  */
class MatchingTest extends FreeSpec {
val totalBalances = List(26470, 2140, 1600, 3480, 2210) // итоговый сумма балансов
// очередность исполнения - сначало используются заявки которые есть в стакане, затем выставляется ордер
  "Проверка стакана" - {

    val data = new MockupOrders
    val clients = new MockupClients

    "на покупку" in {
      val clients = new MockupClients
      TradeSystem.loadAllClients(clients)
      val currentOrder = data.next
      while (!data.isEmpty) {
        TradeSystem.execute(data.next)
      }
      //println(TradeSystem.market((currentOrder.ticket, currentOrder.direction)).dequeueAll)
      //println(TradeSystem.market((currentOrder.ticket, currentOrder.direction.reverse)).dequeueAll)

      TradeSystem.backUnfinishedOrders

    }
    "на продажу" in {}
  }

  "очередность" - {
    "с одной ценой" in {}
    "выставление и исполнение" in {}
  }
  // очередность ордеров с одной ценой - сначало более рание ордера выполняются
  // пакетное исполнение используя цепочку ордеров (если если например покупка не удволетворена с одного ордера, и есть доступные ордера с продажей)

  // ограничения на покупку (пока есть деньги на счету, ордера не снимаются)

  // ограничения на продажу (пока есть в наличии бумаги, ордера не снимаются)

}

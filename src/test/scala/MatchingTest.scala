import org.scalatest.FreeSpec
import dataServices._
/**
  * Created by Kotobotov.ru on 13.02.2019.
  */
class MatchingTest extends FreeSpec {

// очередность исполнения - сначало используются заявки которые есть в стакане, затем выставляется ордер
"Проверка стакана" -{

  val data = new MockupOrders
  val clients = new MockupClients
  "на покупку" in {
println(data.next)
println(clients.next)
println(clients.next)
println(clients.next)
println(clients.next)
println(clients.next)
println(clients.next)
println(clients.next)
println(clients.next)
println(clients.next)
//sellOrder(sellQue.dequeue())
//sellOrder(sellQue.dequeue())
//sellOrder(sellQue.dequeue())
//sellOrder(sellQue.dequeue())

  }
  "на продажу" in {}
}


  "очередность" -{
  "с одной ценой" in {

  }
  "выставление и исполнение" in {

  }
}
  // очередность ордеров с одной ценой - сначало более рание ордера выполняются
  // пакетное исполнение используя цепочку ордеров (если если например покупка не удволетворена с одного ордера, и есть доступные ордера с продажей)

  // ограничения на покупку (пока есть деньги на счету, ордера не снимаются)

  // ограничения на продажу (пока есть в наличии бумаги, ордера не снимаются)

}
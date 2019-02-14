import config.Files
import dataServices._
import config.Files._
import model.Order
/**
 * Created by Kotobotov.ru on 13.02.2019.
 */
object Matching extends App {
  lazy val buyRule = 0 // покупать только столько сколько есть денег (ордер остается но не выполняется)
  lazy val sellRule = 0 // продавать только столько сколько есть бумаг

  def buyOrder(o: Order) = o.price.toDouble + ((Files.ORDERS_LIMIT-o.id) / Files.ORDERS_LIMIT) // заявки обрабатываются согласно порядку, порядок мы учитываем вместе с ценой, количество учитываемых заявок ограничен точностью и размером данных, ограничение записанно в ORDERS_LIMIT чтоб не появлялось ошибок
  def sellOrder(o: Order) = -o.price.toDouble - (o.id / Files.ORDERS_LIMIT)

  val sellQue = scala.collection.mutable.PriorityQueue.empty[Order](Ordering.by(sellOrder)) // используется "куча(пирамида)" для эффективной сортировки порядка заявок в стакане
  val buyQue = scala.collection.mutable.PriorityQueue.empty[Order](Ordering.by(buyOrder))

  val dataStore = new OrdersFromFile

  while(!dataStore.isEmpty){
    val currentOrder =  dataStore.next
    TradeSystem.execute(currentOrder)

  }

    sellQue.dequeue()

  case object TradeSystem{
    //def stakan(tiket:String) = Map("C" -> scala.collection.mutable.PriorityQueue[Order])
    def execute(order:Order) ={

    }

  }
}

import config.Files
import services._
import model._

/**
  * Created by Kotobotov.ru on 13.02.2019.
  */
object Matching extends App {
  lazy val buyRule = 0 // покупать  столько сколько есть денег (ордер остается но не выполняется)
  lazy val sellRule = 0 // продавать  столько сколько есть бумаг

  val dataStore = new OrdersFromFile

  while (!dataStore.isEmpty) {
    // использую проход по итератору с такой идей чтоб можно было обрабатывать гиганские файлы, не загружая логи в память)
    val currentOrder = dataStore.next
    TradeSystem.execute(currentOrder)
  }

  TradeSystem.backUnfinishedOrders
  TradeSystem.writeState
}

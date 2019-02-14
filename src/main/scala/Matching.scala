import config.Files
import dataServices._
import model._

/**
  * Created by Kotobotov.ru on 13.02.2019.
  */
object Matching extends App {
  lazy val buyRule = 0 // покупать  столько сколько есть денег (ордер остается но не выполняется)
  lazy val sellRule = 0 // продавать  столько сколько есть бумаг

  val dataStore = new OrdersFromFile

  while (!dataStore.isEmpty) { // использую проход по итератору с такой идей чтоб можно было обрабатывать гиганские файлы, не загружая логи в память)
    val currentOrder = dataStore.next
    TradeSystem.execute(currentOrder)
  }

  case object TradeSystem {
    def buyOrder(o: Order) =
      o.price.toDouble + ((Files.ORDERS_LIMIT - o.id) / Files.ORDERS_LIMIT) // заявки обрабатываются согласно очередности добавления заявки и ее цене, есть ограничение по которому мы отслеживаием порядок, оно указанно в config.Files.ORDERS_LIMIT
    def sellOrder(o: Order) = -o.price.toDouble - (o.id / Files.ORDERS_LIMIT)

    // используется "куча(пирамида)" для эффективной сортировки порядка заявок в стакане
    def CreateBuyingQue: scala.collection.mutable.PriorityQueue[Order] =
      scala.collection.mutable.PriorityQueue.empty[Order](Ordering.by(buyOrder))
    def CreateSellingQue: scala.collection.mutable.PriorityQueue[Order] =
      scala.collection.mutable.PriorityQueue
        .empty[Order](Ordering.by(sellOrder))
    var clients = Array.empty[Client]
    val market: Map[(String, Direction),
                    scala.collection.mutable.PriorityQueue[Order]] = Map(
      ("A", Buy) -> CreateBuyingQue,
      ("B", Buy) -> CreateBuyingQue,
      ("C", Buy) -> CreateBuyingQue,
      ("D", Buy) -> CreateBuyingQue,
      ("A", Sell) -> CreateSellingQue,
      ("B", Sell) -> CreateSellingQue,
      ("C", Sell) -> CreateSellingQue,
      ("D", Sell) -> CreateSellingQue
    )
    def putToStakan(order: Order) = {
      market(order.ticket, order.direction.reverse).dequeue()
      market(order.ticket, order.direction) += order
    }

    def fromStakan(order: Order) = {}

    def loadClients(clients: Array[Client]) = this.clients = clients
    //def stakan(tiket:String) = Map("C" -> scala.collection.mutable.PriorityQueue[Order])
    def execute(order: Order) = {

      order match {
        case Order(_, clientId, direction, ticket, price, amount) =>
      }

    }

  }
}

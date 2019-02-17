package services
import config.Files
import model._

import scala.collection.mutable

/**
  * Created by Kotobotov.ru on 15.02.2019.
  */
object TradeSystem {

  private val clientStore = mutable.HashMap.empty[String, Client]
  def loadClient(client: Client) = clientStore.put(client.id, client)
  def loadAllClients(clients: ClientService) =
    while (!clients.isEmpty) loadClient(clients.next)

  def fromMarketToClient(order: Order): Boolean = {
    println("processed: " + order.toString)
    order match {
      case Order(_, _, Sell, ticket, _, amount) =>
        clientStore(order.clientId).stocks(ticket) += amount
        true
      case Order(_, _, Buy, _, price, amount) =>
        clientStore(order.clientId).balance += (amount * price)
        true
    }
  }
  def backUnfinishedOrders: Unit = {
    for {
      stakan <- market.values
      order <- stakan
    } fromMarketToClient(order)
  }

  val market
    : Map[(String, Direction), scala.collection.mutable.PriorityQueue[Order]] =
    Map(
      ("A", Buy) -> CreateBuyingQue,
      ("B", Buy) -> CreateBuyingQue,
      ("C", Buy) -> CreateBuyingQue,
      ("D", Buy) -> CreateBuyingQue,
      ("A", Sell) -> CreateSellingQue,
      ("B", Sell) -> CreateSellingQue,
      ("C", Sell) -> CreateSellingQue,
      ("D", Sell) -> CreateSellingQue
    )

  def writeState = {
    import java.io.{File, PrintWriter}
    val writer = new PrintWriter(new File(Files.RESULT))
    clientStore.values.toList.sortBy(_.id).foreach(client => writer.write(client.toString + "\n"))
    writer.close()
  }

  // заявки обрабатываются согласно очередности добавления заявки и ее цене, есть ограничение
  // по которому мы отслеживаием порядок, оно указанно в config.Files.ORDERS_LIMIT
  def buyOrder(o: Order) =
    o.price.toDouble + ((Files.ORDERS_LIMIT - o.id) / Files.ORDERS_LIMIT)
  def sellOrder(o: Order) = -o.price.toDouble - (o.id / Files.ORDERS_LIMIT)

  // используется "куча(пирамида)" для эффективной сортировки порядка заявок в стакане с учетом очередности их подачи
  def CreateBuyingQue: scala.collection.mutable.PriorityQueue[Order] =
    scala.collection.mutable.PriorityQueue.empty[Order](Ordering.by(buyOrder))
  def CreateSellingQue: scala.collection.mutable.PriorityQueue[Order] =
    scala.collection.mutable.PriorityQueue
      .empty[Order](Ordering.by(sellOrder))

  def orderFromMarket(ticket: String, direction: Direction): Order = {
    // todo решить к чему свести пустое решение или к пустому объекту или к опшену, пока пустая заявка
    if (market(ticket, direction).isEmpty)
      Order(1, "C1", direction, ticket, 0, 0)
    else
      market(ticket, direction).dequeue()
  }
  // для стакана пока не подобрал понятного термина
  def putToStakan(sell: Order, buy: Order): Unit = {
    val usedPrice = if (sell.id < buy.id) sell.price else buy.price // используется цена той заявки которая была раньше в системе
    if (sell.amount != 0 && buy.amount != 0 && sell.price <= buy.price)
      if (sell.amount < buy.amount) {
        fromMarketToClient(sell.copy(price = usedPrice, direction = Buy))
        fromMarketToClient(
          buy.copy(price = usedPrice, direction = Sell, amount = sell.amount)
        )
        putToStakan(
          orderFromMarket(sell.ticket, Sell),
          buy.copy(amount = buy.amount - sell.amount)
        )
      } else {
        fromMarketToClient(
          sell.copy(price = usedPrice, direction = Buy, amount = buy.amount)
        )
        fromMarketToClient(buy.copy(price = usedPrice, direction = Sell))
        putToStakan(
          sell.copy(amount = sell.amount - buy.amount),
          orderFromMarket(buy.ticket, Buy)
        )
      } else {
      market(sell.ticket, Sell) += sell
      market(buy.ticket, Buy) += buy
    }
  }

  def reserve(order: Order): Boolean = {
    order match {
      case Order(_, _, Sell, ticket, _, amount) =>
        val clientStock = clientStore(order.clientId).stocks
        if (clientStock(ticket) >= amount) {
          clientStock(ticket) -= amount
          true
        } else false
      case Order(_, _, Buy, _, price, amount) =>
        val client = clientStore(order.clientId)
        if (client.balance >= (amount * price)) {
          client.balance -= (amount * price)
          true
        } else false
    }
  }

  def execute(order: Order) = {
    if (reserve(order)) order match {
      case Order(_, _, Sell, ticket, _, _) =>
        putToStakan(order, orderFromMarket(ticket, Buy))
      case Order(_, _, Buy, ticket, _, _) =>
        putToStakan(orderFromMarket(ticket, Sell), order)
    }
  }
}

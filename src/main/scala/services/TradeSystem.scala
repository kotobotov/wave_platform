package services
import config.Files
import model._

/**
  * Created by Kotobotov.ru on 15.02.2019.
  */
object TradeSystem {
  def returReserev(order: Order) = {
    println("vozvrat " + order.toString)
  }
  def backUnfinishedOrders: Unit = {
    for {
      stakan <- market.values
      order <- stakan
    } returReserev(order)
  }

  var clients = Array.empty[Client]
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
    //reflect.io.File~.File("filename").writeAll("hello world")
    clients.foreach(client => writer.write(client.toString))
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
    if (sell.amount != 0 && buy.amount != 0 && sell.price <= buy.price)
      if (sell.amount < buy.amount)
        // todo здесь надо списать бабло и бумаги с клиента
        putToStakan(
          orderFromMarket(sell.ticket, Sell),
          buy.copy(amount = buy.amount - sell.amount)
        )
      else
        putToStakan(
          sell.copy(amount = sell.amount - buy.amount),
          orderFromMarket(buy.ticket, Buy)
        )
    else {
      market(sell.ticket, Sell) += sell
      market(buy.ticket, Buy) += buy
    }
  }

  def loadClients(clients: Array[Client]) = this.clients = clients
  def execute(order: Order) = {
    order match {
      case Order(_, clientId, Sell, ticket, price, amount) =>
        putToStakan(order, orderFromMarket(ticket, Buy))
      case Order(_, clientId, Buy, ticket, price, amount) =>
        putToStakan(orderFromMarket(ticket, Sell), order)
    }
  }
}

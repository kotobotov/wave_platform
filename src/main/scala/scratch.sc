import model._
import services._

val file = "C1\t1000\t130\t240\t760\t320"
val data = List(
  Order(1, "C1",Sell, "C", 100, 10),
  Order(2, "C2",Sell, "C", 99, 10),
  Order(3, "C3",Sell, "C", 100, 10),
  Order(4, "C4",Buy, "C", 99, 10),
  Order(5, "C1",Sell, "C", 98, 10),
  Order(6, "C1",Sell, "C", 100, 10),
  Order(7, "C1",Sell, "C", 99, 10),
  Order(8, "C1",Buy, "C", 100, 10),
  Order(9, "C1",Buy, "C", 100, 10),
  Order(10, "C1",Buy, "C", 100, 120),
  Order(11, "C1",Buy, "C", 100, 10),
  Order(12, "C4",Buy, "C", 50, 1000),
  Order(13, "C4",Buy, "C", 1000, 100),
  Order(14, "C4",Buy, "C", 500, 10),
  Order(15, "C4",Buy, "C", 1000, 10),
).toIterator
val clients = new MockupClients
val currentOrder = data.next
while (!data.isEmpty) {
  TradeSystem.execute(data.next)
}
currentOrder.ticket
TradeSystem.market((currentOrder.ticket, currentOrder.direction)).dequeueAll
TradeSystem.market((currentOrder.ticket, currentOrder.direction.reverse)).dequeueAll
//TradeSystem.backUnfinishedOrders

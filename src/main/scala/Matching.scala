import services._

/**
  * Created by Kotobotov.ru on 13.02.2019.
  */
object Matching extends App {
  println("Start processing...")
  val orderStore = new OrdersFromFile
  val clientStore = new ClientsFromFile
  TradeSystem.loadAllClients(clientStore)

  while (!orderStore.isEmpty) {
    // использую проход по итератору с такой идей чтоб можно было обрабатывать гиганские файлы, не загружая логи в память)
    val currentOrder = orderStore.next
    TradeSystem.execute(currentOrder)
  }

  TradeSystem.backUnfinishedOrders
  TradeSystem.writeState
  println("All done")
}

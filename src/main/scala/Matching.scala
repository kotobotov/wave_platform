import dataServices._
import model.Order
/**
 * Created by Kotobotov.ru on 13.02.2019.
 */
object Matching extends App {
  val ORDERS_LIMIT = 1000000.toDouble

  val orderList ="""C8	b	C	15	4
                   |C2	s	C	14	5
                   |C2	s	C	13	2
                   |C9	b	B	6	4
                   |C4	b	D	5	4
                   |C8	b	D	4	5
                   |C8	b	A	11	1
                   |C6	b	C	15	1""".stripMargin.split("\n").toList

  val clients = """C1	1000	130	240	760	320
                  |C2	4350	370	120	950	560
                  |C3	2760	0	0	0	0
                  |C4	560	450	540	480	950
                  |C5	1500	0	0	400	100
                  |C6	1300	890	320	100	0
                  |C7	750	20	0	790	0
                  |C8	7000	90	190	0	0
                  |C9	7250	190	190	0	280""".stripMargin.split("\n").toList


  lazy val buyRule = 0 // покупать только сколько есть денег (ордер остается но не выполняется)
  lazy val sellRule = 0 // продавать только сколько есть бумаг

  def buyOrder(o: Order) = o.price.toDouble + ((ORDERS_LIMIT-o.id) / ORDERS_LIMIT) // заявки обрабатываются согласно порядку, порядок мы учитываем вместе с ценой, количество учитываемых заявок ограничен точностью и размером данных, ограничение записанно в ORDERS_LIMIT чтоб не появлялось ошибок
  def sellOrder(o: Order) = -o.price.toDouble - (o.id / ORDERS_LIMIT)

  val sellQue = scala.collection.mutable.PriorityQueue.empty[Order](Ordering.by(sellOrder)) // используется "куча(пирамида)" для эффективной сортировки порядка заявок в стакане
  val buyQue = scala.collection.mutable.PriorityQueue.empty[Order](Ordering.by(buyOrder)) // используется "куча(пирамида)" для эффективной сортировки порядка заявок в стакане

  val dataStore = new OrdersFromFile

  while(!dataStore.isEmpty){
    val currentOrder =  dataStore.next
    TradeSystem.execute(currentOrder)

  }

    sellQue.dequeue()

  case object TradeSystem{
    def stakan(tiket:String) = Map("C" -> scala.collection.mutable.PriorityQueue[Order])
    def execute(order:Order) ={

      sellQue.enqueue(
        inputData:_*
      )

      buyQue.enqueue(
        inputData:_*
      )

    }

  }
}

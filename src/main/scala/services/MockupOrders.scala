package services

import model._

/**
 * Created by Kotobotov.ru on 13.02.2019.
 */

class MockupOrders extends OrderService {
  private val inputData =  List(
    Order(1, "C1",Sell, "C", 100, 10),
    Order(2, "C2",Sell, "C", 99, 10),
    Order(3, "C3",Sell, "C", 100, 10),
    Order(4, "C4",Buy, "C", 99, 10),
    Order(5, "C1",Sell, "C", 98, 10),
    Order(6, "C1",Sell, "C", 100, 10),
    Order(7, "C1",Sell, "C", 99, 10),
    Order(8, "C6",Buy, "C", 100, 10),
    Order(9, "C1",Buy, "C", 100, 10),
    Order(10, "C1",Buy, "C", 100, 10),
    Order(11, "C3",Buy, "C", 100, 10),
    Order(12, "C4",Buy, "C", 50, 10),
    Order(13, "C6",Buy, "C", 10, 100),
    Order(14, "C4",Buy, "C", 500, 10),
    Order(15, "C4",Buy, "C", 1000, 10),
  ).toIterator
  override def next: Order = inputData.next()
  override def isEmpty: Boolean = inputData.isEmpty
}


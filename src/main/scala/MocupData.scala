/**
 * Created by Kotobotov.ru on 13.02.2019.
 */

trait MokupData{
  private val inputData =  List(
    Order(1, "C1","S", "C", 100, 10),
    Order(2, "C2","S", "C", 99, 10),
    Order(3, "C3","S", "C", 100, 10),
    Order(4, "C4","S", "C", 99, 10),
    Order(5, "C1","S", "C", 98, 10),
    Order(6, "C1","S", "C", 100, 10),
    Order(7, "C1","S", "C", 99, 10),
    Order(8, "C1","S", "C", 100, 10)
  ).toIterator

  def isEmpty = inputData.isEmpty
  def next = inputData.next()

}


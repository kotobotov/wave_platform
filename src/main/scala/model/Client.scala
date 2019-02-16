package model
import scala.collection.mutable

/**
  * Created by Kotobotov.ru on 14.02.2019.
  */
case class Client(id: String) {
  var balance: Int = 0
  val stocks = mutable.HashMap("A" -> 0, "B" -> 0, "C" -> 0, "D" -> 0)
  override def toString: String =
    s"$id\t$balance\t${stocks("A")}\t${stocks("B")}\t${stocks("C")}\t${stocks("D")}"
}

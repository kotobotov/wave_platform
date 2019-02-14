package model

/**
 * Created by Kotobotov.ru on 14.02.2019.
 */

sealed trait Direction
case object Buy extends Direction
case object Sell extends Direction

object Direction {
  def apply(input: String): Direction = input match {
    case "b" => Buy
    case _ => Sell
  }
}
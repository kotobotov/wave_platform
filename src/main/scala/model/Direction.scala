package model

/**
  * Created by Kotobotov.ru on 14.02.2019.
  */
sealed trait Direction {
  def reverse:Direction
}
case object Buy extends Direction {
  override def reverse: Direction = Sell
}
case object Sell extends Direction {
  override def reverse: Direction = Buy
}

object Direction {
  def apply(input: String): Direction = input match {
    case "b" => Buy
    case _   => Sell
  }
  def reverse(direction: Direction) = direction match {
    case Buy => Sell
    case _   => Buy
  }
}

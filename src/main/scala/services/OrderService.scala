package services
import model._

import scala.io.Source
import scala.util.Try

/**
 * Created by Kotobotov.ru on 14.02.2019.
 */
trait OrderService {
  def next:Order
  def isEmpty:Boolean
}

object OrderService{
  private var idCounter = 0
  def increasedId = {
    idCounter = idCounter + 1
    idCounter
  }
}
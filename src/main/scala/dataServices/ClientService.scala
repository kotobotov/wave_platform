package dataServices
import scala.io.Source
import model._

/**
 * Created by Kotobotov.ru on 14.02.2019.
 */
trait ClientService {
  def next:Client
  def isEmpty:Boolean
}

package model

/**
 * Created by Kotobotov.ru on 13.02.2019.
 */
case class Order(id:Int, clientId:String, direction:Direction, ticket:String, price:Int, amount:Int)

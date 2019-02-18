import model._
import org.scalatest.FreeSpec
import services._

/**
  * Created by Kotobotov.ru on 13.02.2019.
  */
class MatchingTest extends FreeSpec {
  // val totalBalances = List(26470, 2140, 1600, 3480, 2210) // итоговая сумма балансов
  "Проверка стакана" - {
    // проверяем очередность ордеров с одной ценой - сначало более рание ордера выполняются
    // пакетное исполнение используя цепочку ордеров (если если например покупка не удволетворена с одного ордера, и есть доступные ордера с продажей)
    // ограничения на покупку (по болансу)
    // ограничения на продажу (по количеству наличия бумаг)
    "на покупку" in {
      TradeSystem.prepareSystem
      val clients = new MockupClients
      TradeSystem.loadAllClients(clients)
      val direction = Buy
      val ticket = "C"
      TradeSystem.execute(Order(1, "C1", direction, ticket, 100, 10))
      TradeSystem.execute(Order(2, "C1", direction, ticket, 100000, 10))
      TradeSystem.execute(Order(3, "C1", direction, ticket, 1000, 1))
      TradeSystem.execute(Order(4, "C2", direction, ticket, 100, 10))
      TradeSystem.execute(Order(5, "C4", direction, ticket, 100, 10))
      TradeSystem.execute(Order(6, "C3", direction, ticket, 100, 10))
      TradeSystem.execute(Order(7, "C7", direction, ticket, 100, 10))
      TradeSystem.execute(Order(8, "C6", direction, ticket, 90, 10))
      TradeSystem.execute(Order(9, "C5", direction, ticket, 110, 10))
      TradeSystem.execute(Order(10, "C2", direction, ticket, 100, 40))
      TradeSystem.execute(Order(10, "C2", direction, ticket, 10, 400))
      TradeSystem.execute(Order(10, "C2", direction, ticket, 1, 4000))
      assert(
        TradeSystem.market((ticket, direction)).dequeueAll ==
          Vector(
            Order(9, "C5", Buy, ticket, 110, 10),
            Order(1, "C1", Buy, ticket, 100, 10),
            Order(4, "C2", Buy, ticket, 100, 10),
            Order(6, "C3", Buy, ticket, 100, 10),
            Order(8, "C6", Buy, ticket, 90, 10)
          )
      )
    }
    "на продажу" in {
      TradeSystem.prepareSystem
      val clients = new MockupClients
      TradeSystem.loadAllClients(clients)
      val direction = Sell
      val ticket = "C"
      TradeSystem.execute(Order(1, "C1", direction, ticket, 100, 10))
      TradeSystem.execute(Order(2, "C1", direction, ticket, 10000000, 10))
      TradeSystem.execute(Order(3, "C1", direction, ticket, 100, 1000000))
      TradeSystem.execute(Order(4, "C2", direction, ticket, 100, 10))
      TradeSystem.execute(Order(5, "C4", direction, ticket, 100, 10))
      TradeSystem.execute(Order(6, "C3", direction, ticket, 100, 10))
      TradeSystem.execute(Order(7, "C7", direction, ticket, 100, 10))
      TradeSystem.execute(Order(8, "C6", direction, ticket, 90, 10))
      TradeSystem.execute(Order(9, "C5", direction, ticket, 110, 10))
      assert(
        TradeSystem.market((ticket, direction)).dequeueAll ==
          Vector(
            Order(8, "C6", Sell, ticket, 90, 10),
            Order(1, "C1", Sell, ticket, 100, 10),
            Order(4, "C2", Sell, ticket, 100, 10),
            Order(5, "C4", Sell, ticket, 100, 10),
            Order(7, "C7", Sell, ticket, 100, 10),
            Order(9, "C5", Sell, ticket, 110, 10),
            Order(2, "C1", Sell, ticket, 10000000, 10)
          )
      )
    }
  }

  "проверка корректности остатков" - {
    "используя генератор тестов" in {}
    "демонстрация после только при личном обращении" in {
      // оценив хамское поведение эйчаров этой крайне сомнительной компании, решил не выкладывать генератор
      // если интересен генератор то нужно ко мне обращаться
    }
  }
}

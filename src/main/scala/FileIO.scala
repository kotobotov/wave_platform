/**
 * Created by Kotobotov.ru on 13.02.2019.
 */


trait IO {

}

class FileIO extends IO{

  private var idCounter = 0
  //IO.parse
  private def increasedId = {
    idCounter = idCounter+1
    idCounter
  }

  def parse(input:String) = {
    val (
      client: String,
      direction: String,
      ticket: String,
      price: Int,
      amount: Int
      ) = input.split("\t")
    Order(increasedId,
      client,
      direction,
      ticket,
      price,
      amount)
  }
}

package order

import org.joda.time.DateTime

import scala.util.Try

case class Receiver(
                   name: String,
                   numberPhone: String,
                   address: String,
                   ){

}

case class Order(
                id: Long,
                userId: Long,
                receiver: Receiver,
                createdAt: DateTime,
                price: Long,
                productId: Long,
                status: OrderStatus.Value,
                number: Long,
                url: String
                )  {

}



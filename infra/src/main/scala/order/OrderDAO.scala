package order

import common.AbstractDao
import scalikejdbc._

import scala.util.Try

class OrderDAO extends AbstractDao {

  def store(order: OrderRecord)(implicit s: DBSession = AutoSession): Try[OrderRecord] = Try {
    val id = sql"INSERT INTO myapp.order (user_id, name_receiver,number_phone,address,created_at,price,product_id, status, number, image) VALUES (${order.userId}, ${order.nameReciver}, ${order.numberPhone}, ${order.address},${order.createdAt}, ${order.price}, ${order.productId}, ${order.status}, ${order.number}, ${order.url})"
      .updateAndReturnGeneratedKey().apply().toInt
    order.copy(id = id)
  }
  //order was in cart prepare to buy
  def findOrderDraftByUserId(id: Long)(implicit s: DBSession = AutoSession): Try[Seq[OrderRecord]] = Try {
    sql"SELECT * from myapp.order where user_id = ${id} and status = 'DRAFT' "
      .map(o => OrderRecord(o)).list().apply()
  }

  def storeReceiver(userId: Long, name: String, phone: String, address: String)(implicit s: DBSession = AutoSession): Try[Int] = Try {
    sql"UPDATE myapp.order SET name_receiver = ${name}, number_phone = ${phone}, address = ${address} where user_id = ${userId} and status = 'DRAFT'"
      .updateAndReturnGeneratedKey().apply().toInt
  }

  def destroy(orderId: Long)(implicit s: DBSession = AutoSession): Try[Int] = Try {
    sql"DELETE FROM myapp.order WHERE id = ${orderId}"
      .update.apply()
  }

  def updateStatus(orderId: Long, status: String)(implicit s: DBSession = AutoSession): Try[Int] = Try {
    sql"UPDATE myapp.order SET status = ${status} where id = ${orderId}"
      .updateAndReturnGeneratedKey().apply().toInt
  }

  def updateStatusAll(userId: Long, status: String)(implicit s: DBSession = AutoSession): Try[Int] = Try {
    sql"UPDATE myapp.order SET status = ${status} where user_id = ${userId}"
      .updateAndReturnGeneratedKey().apply().toInt
  }
  //view history
  def findAllOrderByUserId(id: Long)(implicit s: DBSession = AutoSession): Try[Seq[OrderRecord]] = Try {
    sql"SELECT * from myapp.order where user_id = ${id}"
      .map(o => OrderRecord(o)).list().apply()
  }

  def findRecentOrdered()(implicit s: DBSession = AutoSession): Try[Seq[OrderRecord]] = Try {
    sql"SELECT * from myapp.order"
      .map(o => OrderRecord(o)).list().apply()
  }

  override def findByIdString(idString: String): Try[Nothing] = ???

  override def findAll: Try[Seq[Nothing]] = ???

  override def store(t: Nothing): Try[Nothing] = ???

  override def updateRecord(t: Nothing): Try[Int] = ???

  override def destroy(idString: String): Try[Int] = ???
}

package order

import com.google.inject.Inject
import common.{ AbstractDao, AbstractRepository }
import org.joda.time.DateTime
import scala.util.Try

class OrderRepository @Inject() (orderDAO: OrderDAO) {

  val dao = orderDAO

  def saveToCart(newItem: Order): Try[Order] = {
    dao.store(entity2Record(newItem)).map(record2Entity)
  }

  def findOrderDrafByUserId(userId: Long): Try[Seq[Order]] = Try {
    dao.findOrderDraftByUserId(userId).get.map(record2Entity)
  }

  def findAllOrderByUserId(userId: Long): Try[Seq[Order]] = Try {
    dao.findAllOrderByUserId(userId).get.map(record2Entity)
  }

  def saveInfoReceiver(userId: Long, name: String, phone: String, number: String): Try[Int] = {
    dao.storeReceiver(userId, name, phone, number)
  }

  def remove(orderId: Long): Try[Int] = {
    dao.destroy(orderId)
  }

  def updateStatusById(orderId: Long, status: String): Try[Int] = {
    dao.updateStatus(orderId, status)
  }

  def updateStatusByUserId(userId: Long, status: String): Try[Int] = {
    dao.updateStatusAll(userId, status)
  }

  def findImageRecent(): Try[Seq[Order]] = Try {
    dao.findRecentOrdered().get.map(record2Entity)
  }

  def record2Entity(record: OrderRecord): Order = {
    Order(
      id = record.id,
      userId = record.userId,
      receiver = Receiver(record.nameReciver, record.numberPhone, record.address),
      createdAt = record.createdAt,
      price = record.price,
      productId = record.productId,
      status = OrderStatus.withName(record.status),
      number = record.number,
      url = record.url)
  }

  def entity2Record(entity: Order): OrderRecord = {
    OrderRecord(
      id = entity.id,
      userId = entity.userId,
      nameReciver = entity.receiver.name,
      numberPhone = entity.receiver.numberPhone,
      address = entity.receiver.address,
      createdAt = entity.createdAt,
      price = entity.price,
      productId = entity.productId,
      status = entity.status.toString,
      number = entity.number,
      url = entity.url)
  }
}


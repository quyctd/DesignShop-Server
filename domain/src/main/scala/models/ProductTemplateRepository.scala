package models

import com.google.inject.Inject
import product_template.{ ProductTemplateDao, ProductTemplateRecord }

import scala.util.Try

class ProductTemplateRepository @Inject() (productTemplateDao: ProductTemplateDao) {
  val dao = productTemplateDao

  def findProductTemplateByProductId(id: Long): Try[ProductTemplate] = {
    dao.findByProductId(id).map(record2Entity)
  }
  def record2Entity(record: ProductTemplateRecord): ProductTemplate = {
    models.ProductTemplate(
      id = record.id,
      productId = record.productId,
      width = record.width,
      height = record.height,
      url = record.url,
      right = record.right,
      left = record.left,
      top = record.top,
      bottom = record.bottom)
  }

  //  def entity2Record(entity: Order): OrderRecord = {
  //    OrderRecord(
  //      id = entity.id,
  //      userId = entity.userId,
  //      nameReciver = entity.receiver.name,
  //      numberPhone = entity.receiver.numberPhone,
  //      address = entity.receiver.address,
  //      createdAt = entity.createdAt,
  //      price = entity.price,
  //      productId = entity.productId,
  //      status = entity.status.toString,
  //      number = entity.number)
  //  }
}

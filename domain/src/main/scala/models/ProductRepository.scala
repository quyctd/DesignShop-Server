package models

import java.util.UUID

import com.google.inject.Inject
import common.AbstractRepository
import product.{ProductDao, ProductRecord}

import scala.util.{Failure, Success, Try}

class ProductRepository @Inject() (
                                    productDao:         ProductDao,
                                   ) extends AbstractRepository[Product, ProductRecord] {
  override protected val dao = productDao

  def findProductById(id: Long): Try[Product] =  {
    dao.findProductById(id).map(record2Entity)
  }

  override def record2Entity(record: ProductRecord): Product = {
    Product(
      id                            = record.id,
      name                          = record.name,
      image                         = record.image,
      description                   = record.description,
      price                         = record.price)

  }

  override def entity2Record(entity: Product): ProductRecord = {
    ProductRecord(
      id                              = entity.id,
      name                            = entity.name,
      image                           = entity.image,
      description                     = entity.description,
      price                           = entity.price)
  }


}


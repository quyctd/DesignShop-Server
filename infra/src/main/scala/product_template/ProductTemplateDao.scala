package product_template

import common.AbstractDao
import scalikejdbc._

import scala.util.Try

class ProductTemplateDao extends AbstractDao {

  def findByProductId(id: Long)(implicit s: DBSession = AutoSession): Try[ProductTemplateRecord] = Try {
    sql"SELECT * from myapp.product_template where product_id = ${id}"
      .map(o => ProductTemplateRecord(o)).single().apply().getOrElse(throw new Exception("Couldn't find product with id: " + id))
  }

  override def findByIdString(idString: String): Try[Nothing] = ???

  override def findAll: Try[Seq[Nothing]] = ???

  override def store(t: Nothing): Try[Nothing] = ???

  override def updateRecord(t: Nothing): Try[Int] = ???

  override def destroy(idString: String): Try[Int] = ???
}


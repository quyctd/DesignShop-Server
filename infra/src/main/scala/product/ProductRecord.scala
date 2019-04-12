package product

import scalikejdbc._

case class ProductRecord(
  id: Long,
  name: String,
  image: String,
  description: String,
  price: Long)

object ProductRecord extends SQLSyntaxSupport[ProductRecord] {
  override val tableName = "product"

  def apply(c: SyntaxProvider[ProductRecord])(rs: WrappedResultSet): ProductRecord = apply(rs)

  def apply(rs: WrappedResultSet): ProductRecord = ProductRecord(
    id = rs.long("id"),
    name = rs.string("name"),
    image = rs.string("image"),
    description = rs.string("description"),
    price = rs.long("price"))
}
package order

import org.joda.time.DateTime
import scalikejdbc._
import user.UserRecord
import scalikejdbc.jodatime.JodaParameterBinderFactory._
// If you need TypeBinder for joda-time classes
import scalikejdbc.jodatime.JodaTypeBinder._

case class OrderRecord(
  id: Long,
  userId: Long,
  nameReciver: String,
  numberPhone: String,
  address: String,
  createdAt: DateTime,
  price: Long,
  productId: Long,
  status: String,
  number: Long,
  url: String)

object OrderRecord extends SQLSyntaxSupport[OrderRecord] {
  override val tableName = "order"

  def apply(c: SyntaxProvider[OrderRecord])(rs: WrappedResultSet): OrderRecord = apply(rs)
  def apply(rs: WrappedResultSet): OrderRecord = OrderRecord(
    id = rs.long("id"),
    userId = rs.long("user_id"),
    nameReciver = rs.string("name_receiver"),
    numberPhone = rs.string("number_phone"),
    address = rs.string("address"),
    createdAt = rs.get[DateTime]("created_at"),
    price = rs.long("price"),
    productId = rs.long("product_id"),
    status = rs.string("status"),
    number = rs.long("number"),
    url = rs.string("image"))
}

package product_template

import scalikejdbc._

case class ProductTemplateRecord(
  id: Long,
  productId: Long,
  width: Long,
  height: Long,
  url: String,
  left: Long,
  right: Long,
  top: Long,
  bottom: Long)

object ProductTemplateRecord extends SQLSyntaxSupport[ProductTemplateRecord] {
  override val tableName = "product_template"

  def apply(c: SyntaxProvider[ProductTemplateRecord])(rs: WrappedResultSet): ProductTemplateRecord = apply(rs)
  def apply(rs: WrappedResultSet): ProductTemplateRecord = ProductTemplateRecord(
    id = rs.long("id"),
    productId = rs.long("product_id"),
    width = rs.long("width"),
    height = rs.long("height"),
    url = rs.string(columnLabel = "url"),
    right = rs.long(columnLabel = "right"),
    left = rs.long(columnLabel = "left"),
    top = rs.long(columnLabel = "top"),
    bottom = rs.long(columnLabel = "bottom"))

}


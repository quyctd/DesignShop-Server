package models

case class ProductTemplate(
  id: Long,
  productId: Long,
  width: Long,
  height: Long,
  url: String,
  right: Long,
  left: Long,
  top: Long,
  bottom: Long) {}

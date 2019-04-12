package services

import com.google.inject.Inject
import order.{ Order, OrderRepository }
import models.{ Product, ProductTemplate }
import play.api.libs.json.{ JsObject, JsString }

class OrderService @Inject() (
  orderRepository: OrderRepository) {
  def toJson(order: Order): JsObject = JsObject(Seq(
    // format: OFF
    "id" -> JsString(order.id.toString()),
    "price" -> JsString(order.price.toString()),
    "number" -> JsString(order.number.toString()),
    "status" -> JsString(order.status.toString),
    "product_id" -> JsString(order.productId.toString),
    "image" -> JsString(order.url)
  ))


  def toJsonWithProduct(order: Order, product: Product): JsObject = JsObject(Seq(
    "id" -> JsString(order.id.toString()),
    "price" -> JsString(product.price.toString()),
    "number" -> JsString(order.number.toString()),
    "status" -> JsString(order.status.toString),
    "product_id" -> JsString(order.productId.toString),
    "image" -> JsString(product.image),
    "nameProduct" -> JsString(product.name),
    "description" -> JsString(product.description),
    "nameReceiver" -> JsString(order.receiver.name),
    "phone" -> JsString(order.receiver.numberPhone),
    "address" -> JsString(order.receiver.address)
  ))

  def toJsonImageUrl(url: String) : JsObject = JsObject(Seq(
    "url" -> JsString(url)
  ))
}

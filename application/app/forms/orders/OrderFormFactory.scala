package forms.orders

import play.api.data.Form
import play.api.data.Forms._

case class OrderFormItem(
  userId: String,
  productId: String,
  number: String,
  url: String)

case class OrderReceiver(
  userId: String,
  name: String,
  phone: String,
  address: String)

object OrderFormFactory {
  def orderItemForm: Form[OrderFormItem] = Form(
    mapping(
      "userId" -> nonEmptyText,
      "productId" -> nonEmptyText,
      "number" -> nonEmptyText,
      "url" -> nonEmptyText)(OrderFormItem.apply)(OrderFormItem.unapply))

  def orderReceiver: Form[OrderReceiver] = Form(
    mapping(
      "userId" -> nonEmptyText,
      "name" -> nonEmptyText,
      "phone" -> nonEmptyText,
      "address" -> nonEmptyText)(OrderReceiver.apply)(OrderReceiver.unapply))
}


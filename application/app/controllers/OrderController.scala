package controllers
import applications.services.AbstractSecured
import forms.orders.OrderFormFactory
import javax.inject.Inject
import models.{ ProductRepository, ProductTemplateRepository, UserRepository }
import order.{ Order, OrderRepository, OrderStatus, Receiver }
import org.joda.time.DateTime
import play.api.i18n.Messages
import play.api.mvc.{ AbstractController, ControllerComponents, Security }
import services.{ OrderService, ResponseService }

import scala.util.{ Failure, Success }

class OrderController @Inject() (cc: ControllerComponents, orderRepository: OrderRepository, orderService: OrderService, userRepository: UserRepository, productRepository: ProductRepository, productTemplateRepository: ProductTemplateRepository) extends AbstractSecured(userRepository, cc) {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def saveItemToCart() = Action { implicit request =>
    OrderFormFactory.orderItemForm.bindFromRequest.fold(
      errors => {
        Ok(ResponseService.badRequest(Some(errors.errorsAsJson)))
      },
      formData => {
        val newOrder = Order(
          0,
          formData.userId.toInt,
          Receiver("Fake", "Fake", "Fake"),
          DateTime.now,
          productRepository.findProductById(formData.productId.toInt).get.price,
          formData.productId.toInt,
          OrderStatus.DRAFT,
          formData.number.toInt,
          formData.url)
        orderRepository.saveToCart(newOrder) match {
          case Success(order) =>
            Ok(ResponseService.success(
               (orderService.toJson(order))))
          case Failure(error: Error) =>
            Ok(ResponseService.badRequest("user", Messages(error.toString)))
        }

      })
  }

  def payTotal(id: Long) = Action { implicit request =>
    orderRepository.findOrderDrafByUserId(id) match {
      case Success(listOrder) => {
        var total = 0L
        listOrder.foreach(order => total = total + (order.price + order.number))
        Ok(ResponseService.success(
          returnEmptyData = true,
          data = listOrder.map(o => {
            orderService.toJsonWithProduct(
              o,
              productRepository.findProductById(o.productId).get,
              )
          })))
      }
      case Failure(error: Error) =>
        Ok(ResponseService.badRequest("order", Messages(error.toString)))

    }
  }

  def confirmBuy(userId: Long) = Action { implicit request =>
    orderRepository.findOrderDrafByUserId(userId) match {
      case Success(listOrder) => {
        listOrder.foreach(o => orderRepository.updateStatusById(o.id, OrderStatus.PENDING.toString))
        Ok(ResponseService.success())
      }
      case Failure(error: Error) =>
        Ok(ResponseService.badRequest("order", Messages(error.toString)))

    }
  }

  def saveInfoReceiver() = Action { implicit request =>
    OrderFormFactory.orderReceiver.bindFromRequest.fold(
      errors => {
        Ok(ResponseService.badRequest(Some(errors.errorsAsJson)))
      },
      formData => {
        orderRepository.saveInfoReceiver(formData.userId.toInt, formData.name, formData.phone, formData.address) match {
          case Success(order) =>
            Ok(ResponseService.success())
          case Failure(error: Error) =>
            Ok(ResponseService.badRequest("user", Messages(error.toString)))
        }

      })
  }

  def removeOrder(orderId: Long) = Action { implicit request =>
    orderRepository.remove(orderId) match {
      case Success(value) => Ok(ResponseService.success())
      case Failure(error: Error) => Ok(ResponseService.badRequest("order", Messages(error.toString)))
    }
  }

  def updateStatusByOrderId(orderId: Long) = Action { implicit request =>
    orderRepository.updateStatusById(orderId, OrderStatus.PENDING.toString) match {
      case Success(value) => Ok(ResponseService.success())
      case Failure(error: Error) => Ok(ResponseService.badRequest("order", Messages(error.toString)))
    }
  }

  def viewHistoryByUserId(userId: Long) = Action { implicit request =>
    orderRepository.findAllOrderByUserId(userId) match {
      case Success(listOrder) => {
        Ok(ResponseService.success(
          returnEmptyData = true,
          data = listOrder.map(o => {
            orderService.toJsonWithProduct(
              o,
              productRepository.findProductById(o.productId).get,
            )
          })))
      }
      case Failure(error: Error) =>
        Ok(ResponseService.badRequest("order", Messages(error.toString)))
    }
  }

  def viewImageRecentOrdered() = Action { implicit request =>
    orderRepository.findImageRecent() match {
      case Success(listOrder) => {
        Ok(ResponseService.success(
          data = listOrder.map(o => orderService.toJson(o)
        )))
      }
      case Failure(error: Error) =>
        Ok(ResponseService.badRequest("order", Messages(error.toString)))
    }
  }

}


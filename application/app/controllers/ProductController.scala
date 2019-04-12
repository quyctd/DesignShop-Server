package controllers

import applications.services.AbstractSecured
import com.google.inject.Inject
import models.{ ProductRepository, User, UserRepository }
import play.api.mvc.{ Action, ControllerComponents }
import services.{ ProductService, ResponseService }
import user.UserRecord

class ProductController @Inject() (
  userRepository: UserRepository,
  productRepository: ProductRepository,
  productService: ProductService,
  cc: ControllerComponents) extends AbstractSecured[User, UserRecord](userRepository, cc) {
  def findAll() = withoutAuth { implicit request =>

    Ok(ResponseService.success(data = productService.toJsonList(productService.findAll().get), returnEmptyData = true))
  }
}

package controllers

import applications.services.AbstractSecured
import com.google.inject.Inject
import common.UserId
import forms.login.{ LoginFormFactory, UserLoginFormInfo }
import forms.user.RegisterForm
import models.{ User, UserRepository }
import org.mindrot.jbcrypt.BCrypt
import services.{ ResponseService, UserService }
import play.api.Play.current
import play.api.i18n.Messages
import play.api.i18n.Messages.Implicits._
import play.api.mvc.{ Action, AnyContent, ControllerComponents, Security }
import scalikejdbc.config.DBs
import scalikejdbc._

import scala.util.{ Failure, Success }

class AuthController @Inject() (
  userRepository: UserRepository,
  userService: UserService,
  cc: ControllerComponents) extends AbstractSecured(userRepository, cc) {
  DBs.setupAll()
  def login: Action[AnyContent] = Action { implicit request =>
    LoginFormFactory.userLoginForm.bindFromRequest.fold(
      errors => {
        Ok(ResponseService.badRequest(Some(errors.errorsAsJson)))
      },
      formData => userRepository.findByEmail(formData.accountId) match {
        case Success(user) =>
          //TODO: when create account will encode password
          //if (BCrypt.checkpw(formData.password,user.passwordEncrypt))
          if (formData.password == user.passwordEncrypt)
            Ok(ResponseService.success(data = userService.toJson(user)))
              .withSession(Security.username -> user.email)
          else
            Ok(ResponseService.badRequest("password", Messages("user.invalid.password")))
        case Failure(err) => err match {
          case _: Exception => Ok(ResponseService.badRequest("user", Messages("user.invalid.account.id")))
          case _ => Ok(ResponseService.serverError(Messages("system.fails")))
        }
      })
  }

  def logout: Action[AnyContent] = Action { implicit request =>
    Ok(ResponseService.success()).withNewSession
  }

  def create(): Action[AnyContent] = Action { implicit request =>
    RegisterForm.addNew.bindFromRequest.fold(
      errors => {
        Ok(ResponseService.badRequest(Some(errors.errorsAsJson)))
      },
      createUserForm => {
        val newUser = User(
          UserId(1),
          createUserForm.name,
          createUserForm.email,
          createUserForm.password)
        userService.insert(newUser) match {
          case Success(user) =>
            Ok(ResponseService.success(data = Seq(userService.toJson(newUser))))
              .withSession(Security.username -> newUser.email)
          case Failure(_) =>
            Ok(ResponseService.badRequest("user", Messages("error.duplicate")))
        }
      })
  }

}

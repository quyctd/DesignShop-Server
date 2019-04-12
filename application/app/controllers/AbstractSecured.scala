package applications.services

import java.util.Date

import com.google.inject.Inject
import common.AbstractUserRepository
import play.api.i18n.{ I18nSupport }
import play.api.mvc._
import services.ResponseService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success

abstract class AbstractSecured[E, R] @Inject() (userRepository: AbstractUserRepository[E, R], cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  /**
   * Retrieve the connected user email.
   */
  private def username(request: RequestHeader) = request.session.get(Security.username)

  /**
   * Redirect to login if the user in not authorized.
   */
  private def onUnauthorized(request: RequestHeader) = Ok(ResponseService.unAuthorized()).withNewSession

  def withoutAuth(f: Request[AnyContent] => Result): Action[AnyContent] = Action(implicit request =>
    f(request).addingToSession("userTime" -> new Date().getTime.toString))

  /**
   * base authenticated users
   */
  def withAuth(f: => String => Request[AnyContent] => Result) = Security.Authenticated(username, onUnauthorized) {
    user => Action(request => f(user)(request))
  }

  def withAuthAsync(f: => String => Request[AnyContent] => Future[Result]) = Security.Authenticated(username, onUnauthorized) {
    user => Action.async(request => f(user)(request))
  }

  /**
   * base authenticated users with body parser.
   */
  def withAuth[A](bp: BodyParser[A])(f: => String => Request[A] => Result) = Security.Authenticated(username, onUnauthorized) {
    accountId => Action(bp)(request => f(accountId)(request))
  }

  /**
   * Action for authenticated users.
   */
  def isAuthenticated(f: => E => Request[AnyContent] => Result) = withAuth { accountId => implicit request =>
    userRepository.findByAccountIdString(accountId) match {
      case Success(user) =>
        f(user)(request).addingToSession("userTime" -> new Date().getTime.toString)
      case _ => onUnauthorized(request)
    }
  }

  def isAuthenticatedAsync(f: => E => Request[AnyContent] => Future[Result]) = withAuthAsync { accountId => implicit request =>
    userRepository.findByAccountIdString(accountId) match {
      case Success(user) =>
        f(user)(request).map(_.addingToSession("userTime" -> new Date().getTime.toString))
      case _ => Future {
        onUnauthorized(request)
      }
    }
  }

  /**
   * Action for authenticated users with body parser.
   */
  def isAuthenticated[A](bp: BodyParser[A])(f: => E => Request[A] => Result) = withAuth(bp) { accountId => implicit request =>
    userRepository.findByAccountIdString(accountId) match {
      case Success(user) =>
        f(user)(request).addingToSession("userTime" -> new Date().getTime.toString)
      case _ => onUnauthorized(request)
    }
  }

}
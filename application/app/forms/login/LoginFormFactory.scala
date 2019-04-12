package forms.login

import play.api.data.Form
import play.api.data.Forms._

case class UserLoginFormInfo(accountId: String, password: String)

case class AdminLoginFormInfo(orderNumber: String, phoneNumber: String)

object LoginFormFactory {
  val userLoginForm = Form(
    mapping(
			// format: OFF
			"accountId" -> nonEmptyText(0, 200),
			"password" -> nonEmptyText(0, 255))(UserLoginFormInfo.apply)(UserLoginFormInfo.unapply))

	val adminLoginForm = Form {
		mapping(
			"orderNumber" -> nonEmptyText(0, 255),
			"phoneNumber" -> nonEmptyText(4, 4))(AdminLoginFormInfo.apply)(AdminLoginFormInfo.unapply)
    // format: ON
  }
}

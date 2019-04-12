package forms.user

import play.api.data.Form
import play.api.data.Forms._

case class RegisterForm(name: String, email: String, password: String, confirmPassword: String)

object RegisterForm {
  val addNew: Form[RegisterForm] = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText,
      "confirm" -> nonEmptyText)(RegisterForm.apply)(RegisterForm.unapply)
      .verifying("Password do not match", data =>
        data.confirmPassword == data.password))
}


package common

import play.api.libs.Crypto
case class UserId(value: Long)
trait UserBase {
  val id: UserId
  val name: String
  val passwordEncrypt: String
  val email: String

  def passwordMatch(inputPassword: String): Boolean = true

  //  lazy val idStr: String = id.fold("")(CommonService.createViewId(_, "U"))
}

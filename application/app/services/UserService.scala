package services

import com.google.inject.Inject
import models.{ User, UserRepository }
import play.api.libs.json.{ JsObject, JsString }

import scala.util.Try

class UserService @Inject() (
  userRepository: UserRepository) {
  def toJson(user: User): JsObject = JsObject(Seq(
    // format: OFF
    "id" -> JsString(user.id.value.toString()),
    "accountId" -> JsString(user.email),
    "name" -> JsString(user.name)))

  def insert(newUser: User): Try[User] = Try {
    val a = userRepository.findOptionByEmail(newUser.email).get
    userRepository.findOptionByEmail(newUser.email).get match {
      case None => {
        userRepository.save(newUser).get
      }
      case _ => {
        throw new Exception("Duplicate")
      }
    }
  }
}

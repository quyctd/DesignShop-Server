package models

import common.{ UserBase, UserId }

case class User(id: UserId, name: String, email: String, passwordEncrypt: String) extends UserBase {
}

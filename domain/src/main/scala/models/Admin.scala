package models

case class AdminId(value: Long)

case class Admin(adminId: AdminId, name: String, email: String, password: String)
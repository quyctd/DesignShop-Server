package user

import scalikejdbc._
import org.joda.time.DateTime

case class UserRecord(
  id: Long,
  name: String,
  email: String,
  password: String)

object UserRecord extends SQLSyntaxSupport[UserRecord] {
  override val tableName = "user"

  def apply(c: SyntaxProvider[UserRecord])(rs: WrappedResultSet): UserRecord = apply(rs)
  def apply(rs: WrappedResultSet): UserRecord = UserRecord(
    id = rs.long("id"),
    name = rs.string("name"),
    email = rs.string("email"),
    password = rs.string("password"))
}

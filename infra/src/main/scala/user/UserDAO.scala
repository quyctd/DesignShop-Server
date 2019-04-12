package user

import common.AbstractDao
import scalikejdbc._

import scala.util.Try

/**
 * Created by thangkc on 17/12/2015.
 */

class UserDAO extends AbstractDao[UserRecord] {

  val u = UserRecord.syntax("u")
  val column = UserRecord.column
  //  implicit val session: DBSession = AutoSession

  def getUserByEmail(email: String)(implicit s: DBSession = AutoSession): Try[UserRecord] = Try {
    sql"select id, name, email, password from myapp.user where email = ${email}"
      .map(u => UserRecord(u)).single().apply().getOrElse(throw new Exception("Couldn't find user with id: " + email))
  }

  def getOptionUserByEmail(email: String)(implicit s: DBSession = AutoSession): Try[Option[UserRecord]] = Try {
    sql"select id, name, email, password from myapp.user where email = ${email}"
      .map(u => UserRecord(u)).single().apply()
  }

  def save(user: UserRecord)(implicit s: DBSession = AutoSession): Try[UserRecord] = Try {
    val id = sql"INSERT INTO myapp.user (name, email, password) VALUES (${user.name} ,${user.email}, ${user.password})"
      .updateAndReturnGeneratedKey().apply().toInt
    user.copy(id = id)
  }

  override def findAll: Try[Seq[UserRecord]] = ???

  override def updateRecord(t: UserRecord): Try[Int] = ???

  override def destroy(idString: String): Try[Int] = ???

  override def store(t: UserRecord): Try[UserRecord] = ???

  override def findByIdString(idString: String): Try[UserRecord] = ???
}

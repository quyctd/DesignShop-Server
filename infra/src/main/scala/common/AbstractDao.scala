package common

/**
 * Created by thangkc on 14/12/2015.
 */

import scala.util.Try

abstract class AbstractDao[T] {

  def findByIdString(idString: String): Try[T]

  def findAll: Try[Seq[T]]

  def store(t: T): Try[T]

  def updateRecord(t: T): Try[Int]

  def destroy(idString: String): Try[Int]
}

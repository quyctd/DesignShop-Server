package common

/**
 * Created by thangkc on 14/12/2015.
 */

import scala.util.{ Failure, Try }

abstract class AbstractRepository[E, R] {

  protected val dao: AbstractDao[R]

  def record2Entity(record: R): E

  def entity2Record(entity: E): R

  protected def checkInsertInvariant(entity: E): Boolean = true

  protected def checkUpdateInvariant(entity: E): Boolean = true

  protected val CONSTRAINT_VIOLATION_MSG: String = ""

  def findById(id: IdBase): Try[E] = dao.findByIdString(id.toString).map(record2Entity)

  def findAll: Try[Seq[E]] = dao.findAll.map(_.map(record2Entity))

  def insert(entity: E): Try[E] =
    if (checkInsertInvariant(entity))
      dao.store(entity2Record(entity)).map(record2Entity)
    else
      Failure(new Exception())

  def update(entity: E): Try[Boolean] =
    if (checkUpdateInvariant(entity))
      dao.updateRecord(entity2Record(entity)) map (rowAffected => rowAffected == 1)
    else
      Failure(new Exception())

  def delete(id: IdBase): Try[Boolean] = dao.destroy(id.toString) map (rowAffected => rowAffected == 1)

}

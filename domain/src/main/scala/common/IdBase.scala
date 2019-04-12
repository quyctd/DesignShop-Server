package common

import java.util.UUID

/**
 * Created by thangkc on 14/12/2015.
 */
trait IdBase {
  val uuid: UUID
  override val toString = uuid.toString
  val value = uuid

  def ==(idBase: IdBase): Boolean = idBase.toString == uuid.toString

  def !=(idBase: IdBase): Boolean = idBase.toString != uuid.toString
}
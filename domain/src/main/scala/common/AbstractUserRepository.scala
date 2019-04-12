package common

import scala.util.Try

/**
 * Created by thangkc on 12/01/2016.
 */
abstract class AbstractUserRepository[E, R] extends AbstractRepository[E, R] {
  def findByAccountIdString(accountIdString: String): Try[E]
}

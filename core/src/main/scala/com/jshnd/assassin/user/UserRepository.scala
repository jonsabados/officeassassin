package com.jshnd.assassin.user

import com.jshnd.assassin.bindings.Transactional
import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._

class UserRepository {

  @Transactional
  def findByEmail(email: String): Option[User] = inTransaction {
    from(users)(u => where(u.emailAddress === email) select u).headOption
  }

}

package com.jshnd.assassin.persistence.user

import com.jshnd.assassin.persistence.{NoPredicate, FieldEqualsPredicate, BaseQuery, AssassinQuery}

class UserQuery(email: Option[String] = None, fullName: Option[String] = None)
    extends AssassinQuery[User] with BaseQuery {

  def forType = classOf[User]

  def predicate = new NoPredicate()
}

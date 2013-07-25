package com.jshnd.assassin.persistence.user

import com.jshnd.assassin.persistence._

class UserQuery(@QueryField("emailAddress") email: Option[String] = None,
                @QueryField("fullName") fullName: Option[String] = None)
    extends AssassinQuery[User] with BaseQuery with AnnotatedQuery {

  def forType = classOf[User]

}

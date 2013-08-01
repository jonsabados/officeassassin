package com.jshnd.assassin.persistence.user

import com.jshnd.assassin.persistence._
import scala.annotation.meta.field

case class UserQuery(@(QueryField @field)("emailAddress") email: Option[String] = None,
                @(QueryField @field)("fullName") fullName: Option[String] = None)
    extends AssassinQuery[User] with BaseQuery with AnnotatedQuery {

  def forType = classOf[User]

}

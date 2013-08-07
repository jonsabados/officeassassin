package com.jshnd.assassin.user

import scala.annotation.meta.field
import com.jshnd.assassin.query.{QueryField, AnnotatedQuery, BaseQuery, AssassinQuery}

case class UserQuery(@(QueryField @field)("emailAddress") email: Option[String] = None,
                @(QueryField @field)("fullName") fullName: Option[String] = None)
    extends AssassinQuery[User] with BaseQuery with AnnotatedQuery {

  def forType = classOf[User]

}

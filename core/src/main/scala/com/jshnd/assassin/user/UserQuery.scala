package com.jshnd.assassin.user

import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.PagedAssassinQuery
import org.squeryl.Query

case class UserQuery(email: Option[String], handle: Option[String], offset: Int, pageLength: Int)
  extends PagedAssassinQuery[User] {

  def this(email: Option[String], handle: Option[String]) = this(email, handle, 0, Integer.MAX_VALUE)

  def query: Query[User] = from(users)(u =>
    where(
      u.emailAddress === email.?
      and u.handle === handle.?
    )
    select u
  )

}

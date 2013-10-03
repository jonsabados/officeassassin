package com.jshnd.assassin.user

import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.PagedQuery
import org.squeryl.Queryable

class UserQuery(email: Option[String], handle: Option[String], o: Int, pl: Int) extends PagedQuery[User] {

  def query: Queryable[User] = from(users)(u =>
    where(
      u.emailAddress === email.?
      and u.handle === handle.?
    )
    select(u)
  )

  def offset: Int = o

  def pageLength: Int = pl
}

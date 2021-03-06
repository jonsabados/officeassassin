package com.jshnd.assassin.user

import com.jshnd.assassin.persistence.AssassinQuery
import org.squeryl.Query
import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._

case class UserByHandleQuery(handle: String) extends AssassinQuery[User] {
  def query: Query[User] = from(users)(u =>
    where(
      u.handle === handle
    )
    select u
  )
}
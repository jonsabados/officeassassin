package com.jshnd.assassin.user

import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.PagedAssassinQuery
import org.squeryl.Query

case class UserQuery(offset: Int, pageLength: Int)
  extends PagedAssassinQuery[User] {

  def query: Query[User] = from(users)(u =>
    select(u)
  )

}

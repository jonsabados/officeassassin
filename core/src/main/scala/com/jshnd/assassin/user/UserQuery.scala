package com.jshnd.assassin.user

import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.PagedAssassinQuery
import org.squeryl.Query

case class UserQuery(emailAddressLike: Option[String],
                     handleLike: Option[String],
                     fullNameLike: Option[String],
                     offset: Int,
                     pageLength: Int)
  extends PagedAssassinQuery[User] {

  def query: Query[User] = from(users)(u =>
    where(
      (u.emailAddress like emailAddressLike.?)
      and (u.handle like handleLike.?)
      and (u.fullName like fullNameLike.?)
    )
    select(u)
    orderBy(u.emailAddress asc)
  )

}

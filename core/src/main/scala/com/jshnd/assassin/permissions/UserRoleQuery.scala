package com.jshnd.assassin.permissions

import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.PagedAssassinQuery
import org.squeryl.Query

case class UserRoleQuery(username: Option[String], id: Option[Int], offset: Int, pageLength: Int)
  extends PagedAssassinQuery[Role] {

  def this(username: String) = this(Some(username), None, 0, 0)
  def this(id: Int, offset: Int, pageLength: Int) = this(None, Some(id), offset, pageLength)

  def query: Query[Role] = from(roles)(r =>
    where(
      exists(join(userRoles, users)((ur, u) =>
        where(
          ur.roleId === r.id and u.emailAddress === username.? and u.id === id.?
        )
        select ur
        on(ur.userId === u.id)
      )) or
      exists(join(groupRoles, groups, userGroups, users)((gr, g, ug, u) =>
        where(
          gr.roleId === r.id and u.emailAddress === username.? and u.id === id.?
        )
        select gr
        on(gr.groupId === g.id, g.id === ug.groupId, ug.userId === u.id)
      ))
    )
    select r
  )

}

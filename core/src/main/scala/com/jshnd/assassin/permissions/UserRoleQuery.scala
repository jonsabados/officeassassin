package com.jshnd.assassin.permissions

import org.squeryl.PrimitiveTypeMode._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.AssassinQuery
import org.squeryl.Query

case class UserRoleQuery(username: String) extends AssassinQuery[Role] {
  def query: Query[Role] = from(roles)(r =>
    where(
      exists(join(userRoles, users)((ur, u) =>
        where(
          ur.roleId === r.id and u.emailAddress === username
        )
        select ur
        on(ur.userId === u.id)
      )) or
      exists(join(groupRoles, groups, userGroups, users)((gr, g, ug, u) =>
        where(
          gr.roleId === r.id and u.emailAddress === username
        )
        select gr
        on(gr.groupId === g.id, g.id === ug.groupId, ug.userId === u.id)
      ))
    )
    select r
  )

}

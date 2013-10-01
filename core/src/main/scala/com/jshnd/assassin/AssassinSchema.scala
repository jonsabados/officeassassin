package com.jshnd.assassin

import org.squeryl.Schema
import com.jshnd.assassin.user.User
import com.jshnd.assassin.permissions.{UserRole, GroupRole, Role, Group}

object AssassinSchema extends Schema {

  val users = table[User]("users")

  val groups = table[Group]("groups")

  val roles = table[Role]("roles")

  val groupRoles = table[GroupRole]("group_roles")

  val userRoles = table[UserRole]("user_roles")

}

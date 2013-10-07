package com.jshnd.assassin.permissions

import com.jshnd.assassin.user.User

trait RolePermissions {

  def permissions(user: User): Set[String]

}

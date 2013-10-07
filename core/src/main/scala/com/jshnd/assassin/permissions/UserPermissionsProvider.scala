package com.jshnd.assassin.permissions

import com.jshnd.assassin.user.User

trait UserPermissionsProvider {

  def userPermissions(user: User): Set[String]

}

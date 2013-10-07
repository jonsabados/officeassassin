package com.jshnd.assassin.permissions

import com.google.inject.{Singleton => GSingleton, AbstractModule}
import com.jshnd.assassin.user.User
import com.google.inject.name.Names

class DefaultUserPermissionModule extends AbstractModule {

  def configure() {
    bind(classOf[UserPermissionsProvider]).to(classOf[DefaultUserPermissionsProvider]).in(classOf[GSingleton])
    bind(classOf[RolePermissions]).annotatedWith(Names.named("user")).to(classOf[UserPermissions])
    bind(classOf[RolePermissions]).annotatedWith(Names.named("user_admin")).to(classOf[UserAdminPermissions])
  }

}

class UserPermissions extends RolePermissions {
  def permissions(user: User): Set[String] = {
    Set("users:view:*", "users:edit:" + user.id)
  }
}

class UserAdminPermissions extends RolePermissions {
  def permissions(user: User): Set[String] = {
    Set("users:edit:*")
  }
}
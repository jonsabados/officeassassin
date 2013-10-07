package com.jshnd.assassin.permissions

import com.jshnd.assassin.persistence.AssassinStore
import javax.inject.Inject
import com.google.inject.{Key, TypeLiteral, Injector}
import com.jshnd.assassin.user.User
import com.google.inject.name.Names

class DefaultUserPermissionsProvider @Inject() (injector: Injector, store: AssassinStore) extends UserPermissionsProvider {

  def userPermissions(user: User): Set[String] = {
    val allRoles = store.allResults(new UserRoleQuery(user.emailAddress)).results
    permissions(user, allRoles, Set())
  }

  private def permissions(user: User, roles: List[Role], accu: Set[String]): Set[String] = {
    if(roles.isEmpty) accu
    else accu ++ rolePermissionProvider(roles.head).permissions(user) ++ permissions(user, roles.tail, Set())
  }

  private def rolePermissionProvider(role: Role): RolePermissions = {
    injector.getInstance(Key.get(new TypeLiteral[RolePermissions] {}, Names.named(role.name)))
  }

}

package com.jshnd.assassin.rest

import com.google.inject.{TypeLiteral, AbstractModule}
import com.jshnd.assassin.user.User
import com.jshnd.assassin.dto.{RoleDto, UserViewDto}
import com.jshnd.assassin.permissions.Role

class MappingModule extends AbstractModule {
  def userMapper(x: User): UserViewDto = new UserViewDto(Some(x.id), x.emailAddress, x.handle, x.fullName.getOrElse(null))
  def roleMapper(x: Role): RoleDto = new RoleDto(x.name)

  def configure() {
    bind(new TypeLiteral[(User) => UserViewDto] {}).toInstance(userMapper)
    bind(new TypeLiteral[(Role) => RoleDto] {}).toInstance(roleMapper)
  }
}

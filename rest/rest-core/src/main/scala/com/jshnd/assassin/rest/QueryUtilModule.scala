package com.jshnd.assassin.rest

import com.google.inject.{Singleton => GSingleton, TypeLiteral, AbstractModule}
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.dto.{RoleDto, UserViewDto}
import com.jshnd.assassin.user.User
import org.squeryl.Table
import com.jshnd.assassin.permissions.Role

class QueryUtilModule extends AbstractModule {
  def configure() {
    bind(new TypeLiteral[Table[Role]] {}).toInstance(roles)
    bind(new TypeLiteral[QueryUtil[Int, Role, RoleDto]] {}).in(classOf[GSingleton])

    bind(new TypeLiteral[Table[User]] {}).toInstance(users)
    bind(new TypeLiteral[QueryUtil[Int, User, UserViewDto]] {}).in(classOf[GSingleton])
  }
}

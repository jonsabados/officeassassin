package com.jshnd.assassin.user

import com.google.inject.{Inject, TypeLiteral, AbstractModule}
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.bindings.{FindUserByHandle, FindUserByEmail, EnlistNewUser}
import com.jshnd.assassin.persistence.AssassinStore

class UserModule extends AbstractModule {

  @Inject
  var store: AssassinStore = null

  def configure() {
    bind(new TypeLiteral[(User) => User] {}).annotatedWith(classOf[EnlistNewUser]).toInstance((u: User) =>
      store.save(users, u)
    )
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByEmail]).toInstance((e: String) =>
      store.findUnique(new UserQuery(Some(e), None))
    )
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByHandle]).toInstance((h: String) =>
      store.findUnique(new UserQuery(None, Some(h)))
    )

  }

}

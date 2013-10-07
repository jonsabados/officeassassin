package com.jshnd.assassin.user

import com.google.inject.{Singleton => GSingleton, Inject, TypeLiteral, AbstractModule}
import com.jshnd.assassin.bindings.{FindUserByHandle, FindUserByEmail, EnlistNewUser}
import com.jshnd.assassin.persistence.AssassinStore

class UserModule extends AbstractModule {

  @Inject
  var store: AssassinStore = null

  @Inject
  var userController: UserController = null;

  def configure() {
    bind(classOf[UserController]).in(classOf[GSingleton])

    bind(new TypeLiteral[(User) => User] {}).annotatedWith(classOf[EnlistNewUser]).toInstance(u =>
      userController.newUser(u)
    )
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByEmail]).toInstance(e =>
      store.find(new UserQuery(Some(e), None))
    )
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByHandle]).toInstance(h =>
      store.find(new UserQuery(None, Some(h)))
    )

  }

}

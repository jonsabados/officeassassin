package com.jshnd.assassin.user

import com.google.inject.{TypeLiteral, Inject, AbstractModule}
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.{FindUserByEmail, EnlistNewUser}

class UserModule extends AbstractModule {

  @Inject
  val store: AssassinStore = null

  def saveUser(user: User) = store.persist(user)

  def findByEmail(email: String): Option[User] = store.findUnique(new UserQuery(Some(email)))

  def configure() {
    bind(new TypeLiteral[(User) => Unit] {}).annotatedWith(classOf[EnlistNewUser]).toInstance(saveUser)
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByEmail]).toInstance(findByEmail)
  }

}

package com.jshnd.assassin.user

import com.google.inject.{TypeLiteral, Inject, AbstractModule}
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.{FindUserByEmail, SaveUser}

class UserModule extends AbstractModule {

  @Inject
  val store: AssassinStore = null

  def saveUser(user: User) = store.persist(user)

  def findByEmail(email: String): List[User] = store.find(new UserQuery(Some(email)))

  def configure() {
    bind(new TypeLiteral[(User) => Unit] {}).annotatedWith(classOf[SaveUser]).toInstance(saveUser)
    bind(new TypeLiteral[(String) => List[User]] {}).annotatedWith(classOf[FindUserByEmail]).toInstance(findByEmail)
  }

}

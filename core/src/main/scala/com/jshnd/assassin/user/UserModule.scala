package com.jshnd.assassin.user

import com.google.inject.{TypeLiteral, Inject, AbstractModule}
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.SaveUser

class UserModule extends AbstractModule {

  @Inject
  val store: AssassinStore = null

  def saveUser = (user: User) => store.persist(user)

  def configure() {
    bind(new TypeLiteral[(User) => Unit] {}).annotatedWith(classOf[SaveUser]).toInstance(saveUser)
  }

}

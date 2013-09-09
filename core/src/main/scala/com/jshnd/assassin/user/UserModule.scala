package com.jshnd.assassin.user

import com.google.inject.{TypeLiteral, Inject, AbstractModule}
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.{FindUserByHandle, FindUserByEmail, EnlistNewUser}

class UserModule extends AbstractModule {

  @Inject
  val store: AssassinStore = null

  def saveUser(user: User): User = store.persist(user)

  def findByEmail(email: String): Option[User] = store.findUnique(new UserQuery(Some(email)))

  def findByHandle(handle: String): Option[User] = store.findUnique(new UserQuery(None, Some(handle)))

  def configure() {
    bind(new TypeLiteral[(User) => User] {}).annotatedWith(classOf[EnlistNewUser]).toInstance(saveUser)
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByEmail]).toInstance(findByEmail)
    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByHandle]).toInstance(findByHandle)
  }

}

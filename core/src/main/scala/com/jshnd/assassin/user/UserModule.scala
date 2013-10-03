package com.jshnd.assassin.user

import com.google.inject.{Singleton => GSingleton, Inject, TypeLiteral, AbstractModule}
import com.jshnd.assassin.bindings.FindUserByEmail

class UserModule extends AbstractModule {
//
//  @Inject
//  val userRepository: UserRepository = null
//
//  def findByEmail(email: String): Option[User] = userRepository.findByEmail(email)

  def configure() {
//    bind(new TypeLiteral[(User) => User] {}).annotatedWith(classOf[EnlistNewUser]).toInstance(saveUser)
//    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByEmail]).toInstance(findByEmail)
//    bind(new TypeLiteral[(String) => Option[User]] {}).annotatedWith(classOf[FindUserByHandle]).toInstance(findByHandle)
  }

}

package com.jshnd.assassin.persistence.jpa

import com.jshnd.assassin.persistence.user.User

class JpaTypeMapperFactory {


  def mapper[J, A](assassinType: Class[A]): JpaMapper[J, A] = assassinType match {
    case user if assassinType.isAssignableFrom(classOf[User]) => new UserMapper().asInstanceOf[JpaMapper[J, A]]
  }

}

abstract class JpaMapper[J, A] {

  def jpaClass: Class[J]

  def map(in: J): A

}

class UserMapper extends JpaMapper[UserEntity, User] {
  def jpaClass: Class[UserEntity] = classOf[UserEntity]

  def map(in: UserEntity): User = new User(in.id, in.emailAddress, in.handle, in.fullName, in.passwordHash)
}
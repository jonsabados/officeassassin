package com.jshnd.assassin.persistence.jpa

import com.jshnd.assassin.persistence.user.User

class JpaTypeMapperFactory {


  def mapper[J, A](assassinType: Class[A]): JpaMapper[J, A] = assassinType match {
    case user if assassinType.isAssignableFrom(classOf[User]) => new UserMapper().asInstanceOf[JpaMapper[J, A]]
  }

}

abstract class JpaMapper[J, A] {

  def jpaClass: Class[J]

  def mapToAssassin(in: J): A

  def mapTpaoJ(in: A): J

}

class UserMapper extends JpaMapper[UserEntity, User] {
  def jpaClass: Class[UserEntity] = classOf[UserEntity]

  def mapToAssassin(in: UserEntity): User = new User(None, in.emailAddress, in.handle, in.fullName, in.passwordHash)

  def mapTpaoJ(in: User): UserEntity = new UserEntity(in.emailAddress, in.handle, in.fullName, in.passwordHash)
}
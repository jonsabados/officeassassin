package com.jshnd.assassin.persistence.jpa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Inside, FunSpec}
import com.jshnd.assassin.persistence.user.User

@RunWith(classOf[JUnitRunner])
class JpaTypeMapperFactoryTest extends FunSpec {

  describe("JpaTypeMapperFactory") {

    val testObj = new JpaTypeMapperFactory()

    it("Should return UserMappers for classOf[User]") {
      val mapper: JpaMapper[UserEntity, User] = testObj.mapper(classOf[User])
      assert(mapper.getClass === classOf[UserMapper])
    }

  }

  describe("UserMapper") {

    val userMapper = new UserMapper();

    it("Should properly transform users") {

      val input = new UserEntity();
      input.id = 2
      input.emailAddress = "e"
      input.fullName = Some("n")
      input.handle = "h"
      input.passwordHash = "p"

      assert(userMapper.map(input) === User(2, "e", "h", Some("n"), "p"))
    }

  }

}

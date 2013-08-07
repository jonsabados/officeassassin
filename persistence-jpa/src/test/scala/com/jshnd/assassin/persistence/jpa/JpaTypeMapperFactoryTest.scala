package com.jshnd.assassin.persistence.jpa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Inside, FunSpec}
import com.jshnd.assassin.user.User

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

    it("Should properly map userEntities to assassin Users") {

      val input = new UserEntity();
      input.id = 2
      input.emailAddress = "e"
      input.fullName = "n"
      input.handle = "h"
      input.passwordHash = "p"

      assert(userMapper.mapToAssassin(input) === User(Some(2), "e", "h", Some("n"), "p"))
    }

    it("Should properly map userEntities without full names to assassin Users") {

      val input = new UserEntity();
      input.id = 2
      input.emailAddress = "e"
      input.handle = "h"
      input.passwordHash = "p"

      assert(userMapper.mapToAssassin(input) === User(Some(2), "e", "h", None, "p"))
    }

    it("Should properly map assassin Users with ids to UserEntities") {

      val input = new User(Some(1), "e", "h", Some("foo"), "p");
      val result = userMapper.mapToJpa(input)

      assert(result.id === 1)
      assert(result.emailAddress === "e")
      assert(result.handle === "h")
      assert(result.fullName === "foo")
      assert(result.passwordHash === "p")
    }

    it("Should properly map assassin Users without ids to UserEntities") {

      val input = new User(None, "e", "h", Some("foo"), "p");
      val result = userMapper.mapToJpa(input)

      assert(result.id === null)
      assert(result.emailAddress === "e")
      assert(result.handle === "h")
      assert(result.fullName === "foo")
      assert(result.passwordHash === "p")
    }

    it("Should properly map assassin Users without full names to UserEntities") {

      val input = new User(None, "e", "h", None, "p");
      val result = userMapper.mapToJpa(input)

      assert(result.fullName === null)
    }

  }

}

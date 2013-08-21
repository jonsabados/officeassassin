package com.jshnd.assassin.rest

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.apache.shiro.authc.{SimpleAuthenticationInfo, UsernamePasswordToken}
import com.jshnd.assassin.user.User

@RunWith(classOf[JUnitRunner])
class AssassinAuthenticationSourceTest extends FunSpec {

  describe("AssassinAuthenticationSource") {

    val token = new UsernamePasswordToken()
    token.setUsername("test@example.com")

    val validUser = new User(Some(1), "test@example.com", "test", Some("Test User"), "12345")

    def happyPathFind(x: String): Option[User] = {
      if (!x.equals("test@example.com")) fail("Wrong username passed")
      Some(validUser)
    }

    it("Should return a user with an invalid hash when no user found") {
      val result = new AssassinAuthenticationSource((String) => None).authenticationInfo(token).asInstanceOf[SimpleAuthenticationInfo]
      assert(result.getCredentials === AssassinAuthenticationSource.WONT_MATCH_HASH)
      assert(result.getPrincipals.asList().get(0) === User(None, "test@example.com", "Nope", None, AssassinAuthenticationSource.WONT_MATCH_HASH))
    }

    it("Should return a user when found") {
      val result = new AssassinAuthenticationSource(happyPathFind).authenticationInfo(token).asInstanceOf[SimpleAuthenticationInfo]
      assert(result.getCredentials === "12345")
      assert(result.getPrincipals.asList().get(0) === validUser)
    }

    it("Should use the username as the salt") {
      val result = new AssassinAuthenticationSource(happyPathFind).authenticationInfo(token).asInstanceOf[SimpleAuthenticationInfo]
      assert(result.getCredentialsSalt.getBytes === "test@example.com".getBytes)
    }

  }

}

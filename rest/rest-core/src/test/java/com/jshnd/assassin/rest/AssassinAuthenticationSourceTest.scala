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

    it("Should return a user with an invalid hash when no user found") {
      val result = new AssassinAuthenticationSource((String) => List()).authenticationInfo(token).asInstanceOf[SimpleAuthenticationInfo]
      assert(result.getCredentials === AssassinAuthenticationSource.WONT_MATCH_HASH)
      assert(result.getPrincipals.asList().get(0) === User(None, "test@example.com", "Nope", None, AssassinAuthenticationSource.WONT_MATCH_HASH))
    }

    it("Should return a user when found") {
      val found = new User(Some(1), "test@example.com", "test", Some("Test User"), "12345")
      def testIt(x: String): List[User] = {
        if(!x.equals("test@example.com")) fail("Wrong username passed")
        List(found)
      }
      val result = new AssassinAuthenticationSource(testIt).authenticationInfo(token).asInstanceOf[SimpleAuthenticationInfo]
      assert(result.getCredentials === "12345")
      assert(result.getPrincipals.asList().get(0) === found)
    }

    it("Should use the username as the salt") {
      val found = new User(Some(1), "test@example.com", "test", Some("Test User"), "12345")
      def testIt(x: String): List[User] = {
        if(!x.equals("test@example.com")) fail("Wrong username passed")
        List(found)
      }
      val result = new AssassinAuthenticationSource(testIt).authenticationInfo(token).asInstanceOf[SimpleAuthenticationInfo]
      assert(result.getCredentialsSalt.getBytes === "test@example.com".getBytes)
    }

    it("Should blow up when more than one user is found") {
      val found = new User(Some(1), "test@example.com", "test", Some("Test User"), "12345")
      intercept[IllegalStateException] {
        new AssassinAuthenticationSource((x) => List(found, found)).authenticationInfo(token)
      }
    }

  }

}

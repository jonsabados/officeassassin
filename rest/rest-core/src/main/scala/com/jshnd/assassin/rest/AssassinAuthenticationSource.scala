package com.jshnd.assassin.rest

import com.jshnd.shiro.AuthenticationInfoSource
import org.apache.shiro.authc.{SimpleAuthenticationInfo, UsernamePasswordToken, AuthenticationInfo, AuthenticationToken}
import com.google.inject.Inject
import com.jshnd.assassin.user.User
import com.jshnd.assassin.bindings.FindUserByEmail
import org.apache.shiro.util.SimpleByteSource

object AssassinAuthenticationSource {
  val WONT_MATCH_HASH = "!!!We still want hashing to be performed to prevent timing attacks to deduce valid users - nothing will hash to this!!!!"
}

class AssassinAuthenticationSource @Inject() (@FindUserByEmail userQuery: (String) => Option[User])
  extends AuthenticationInfoSource {

  def authenticationInfo(token: AuthenticationToken): AuthenticationInfo = {
    val email = token.asInstanceOf[UsernamePasswordToken].getUsername
    userQuery(email) match {
      case Some(u) => toInfo(u)
      case _ => toInfo(new User(None, email, "Nope", None, AssassinAuthenticationSource.WONT_MATCH_HASH))
    }
  }

  private def toInfo(user: User): AuthenticationInfo = {
    new SimpleAuthenticationInfo(user, user.passwordHash, new SimpleByteSource(user.emailAddress.getBytes("UTF-8")), "assassin")
  }

}

package com.jshnd.assassin.rest

import com.jshnd.shiro.AuthenticationInfoSource
import org.apache.shiro.authc.{AuthenticationInfo, AuthenticationToken}

class AssassinAuthenticationSource extends AuthenticationInfoSource {
  def authenticationInfo(token: AuthenticationToken): AuthenticationInfo = ???
}

package com.jshnd.shiro

import org.apache.shiro.authc.{AuthenticationInfo, AuthenticationToken}

abstract trait AuthenticationInfoSource {

  def authenticationInfo(token: AuthenticationToken): AuthenticationInfo

}

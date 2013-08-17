package com.jshnd.assassin.rest

import com.jshnd.shiro.AuthorizationInfoSource
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.authz.{SimpleAuthorizationInfo, AuthorizationInfo}

class AssassinAuthorizationSource extends AuthorizationInfoSource {
  //TODO - actually do something
  def authorizationInfo(principals: PrincipalCollection): AuthorizationInfo = new SimpleAuthorizationInfo()
}

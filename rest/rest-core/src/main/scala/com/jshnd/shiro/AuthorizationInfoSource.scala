package com.jshnd.shiro

import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.authz.AuthorizationInfo

abstract trait AuthorizationInfoSource {

  def authorizationInfo(principals: PrincipalCollection): AuthorizationInfo

}

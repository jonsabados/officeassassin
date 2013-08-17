package com.jshnd.shiro

import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authc.{AuthenticationInfo, AuthenticationToken}
import com.google.inject.Inject
import org.apache.shiro.authc.credential.CredentialsMatcher

//TODO -
class InjectionRealm @Inject() (authenticate: AuthenticationInfoSource,
                                authorize: AuthorizationInfoSource,
                                matcher: CredentialsMatcher)
  extends AuthorizingRealm {

  def doGetAuthenticationInfo(token: AuthenticationToken): AuthenticationInfo = authenticate.authenticationInfo(token)
                getCredentialsMatcher
  def doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo = authorize.authorizationInfo(principals)

  override def getCredentialsMatcher = matcher

}

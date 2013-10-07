package com.jshnd.assassin.rest

import scala.collection.JavaConversions._
import com.jshnd.shiro.AuthorizationInfoSource
import org.apache.shiro.subject.PrincipalCollection
import org.apache.shiro.authz.{SimpleAuthorizationInfo, AuthorizationInfo}
import javax.inject.Inject
import com.jshnd.assassin.permissions.UserPermissionsProvider
import com.jshnd.assassin.user.User

class AssassinAuthorizationSource @Inject() (permProvider: UserPermissionsProvider) extends AuthorizationInfoSource {

  def authorizationInfo(principals: PrincipalCollection): AuthorizationInfo = {
    val ret = new SimpleAuthorizationInfo()
    ret.setStringPermissions(permissions(principals.getPrimaryPrincipal.asInstanceOf[User]))
    ret
  }

  def permissions(u: User): Set[String] = permProvider.userPermissions(u)

}

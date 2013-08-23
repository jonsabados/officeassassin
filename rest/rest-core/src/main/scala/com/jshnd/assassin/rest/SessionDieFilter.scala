package com.jshnd.assassin.rest

import javax.servlet._
import javax.servlet.http.HttpServletRequest

/**
 * Cant figure out how to keep shiro from starting sessions... haven't resorted to trying the ini approach but
 * went as far as:
 *
 *  val securityManager = i.getInstance(classOf[org.apache.shiro.mgt.SecurityManager]).asInstanceOf[DefaultWebSecurityManager]
 *  securityManager.getSubjectDAO.asInstanceOf[DefaultSubjectDAO].getSessionStorageEvaluator.asInstanceOf[DefaultSessionStorageEvaluator].setSessionStorageEnabled(false)
 *
 * in the servlet module and sessions still happen, so for now we will just invalidate the damn things every request.
 */
class SessionDieFilter extends Filter {
  def init(p1: FilterConfig) {}

  def doFilter(p1: ServletRequest, p2: ServletResponse, p3: FilterChain) {
    p3.doFilter(p1, p2)
    p1.asInstanceOf[HttpServletRequest].getSession.invalidate()
  }

  def destroy() {}
}

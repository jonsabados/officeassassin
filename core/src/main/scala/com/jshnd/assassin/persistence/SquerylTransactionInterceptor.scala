package com.jshnd.assassin.persistence

import org.squeryl.PrimitiveTypeMode._
import org.aopalliance.intercept.{MethodInvocation, MethodInterceptor}
import org.squeryl.Session
import javax.inject.Inject

class SquerylTransactionInterceptor extends MethodInterceptor {

  @Inject
  val session: Session = _

  def invoke(p1: MethodInvocation): AnyRef = using(session) {
    inTransaction {
      p1.proceed()
    }
  }

}

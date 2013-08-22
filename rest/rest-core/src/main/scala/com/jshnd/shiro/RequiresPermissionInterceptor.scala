package com.jshnd.shiro

import org.aopalliance.intercept.{MethodInvocation, MethodInterceptor}
import org.apache.shiro.util.ThreadContext
import java.lang.annotation.Annotation
import scala._
import scala.AnyRef
import scala.annotation.tailrec

class RequiresPermissionInterceptor extends MethodInterceptor {

  def invoke(p1: MethodInvocation): AnyRef = {
    println("I was invoked")
    val annotation = p1.getMethod.getAnnotation(classOf[RequiresPermission])
    val subject = ThreadContext.getSubject
    val permission = apply(annotation.value(), substitutions(p1.getArguments, p1.getMethod.getParameterAnnotations))
    subject.checkPermission(permission)
    p1.proceed()
  }

  def substitutions(args: Array[AnyRef],
                    annotations: Array[Array[Annotation]],
                    index: Int = 0,
                    accu: List[(AnyRef, Substitution)] = List()): List[(AnyRef, Substitution)] = {
    val nextMatch = annotations.indexWhere(x => x.find(a => a.annotationType().getClass.eq(classOf[Substitution])) != -1, index)
    println("Hi!" + nextMatch)
    if(nextMatch == -1) accu
    else {
      val toAdd = (args(index), annotations(index).find(a => a.annotationType().getClass.eq(classOf[Substitution])).get.asInstanceOf[Substitution])
      substitutions(args, annotations, nextMatch, toAdd :: accu)
    }
  }

  @tailrec
  private def apply(permission: String, toApply: List[(AnyRef, Substitution)]): String = {
    if(toApply.isEmpty) permission
    else apply(apply(permission, toApply.head), toApply.tail)
  }

  private def apply(permission: String, sub: (AnyRef, Substitution)): String = {
    permission.replace("{" + sub._2.value() + "}", sub._1.toString)
  }

}

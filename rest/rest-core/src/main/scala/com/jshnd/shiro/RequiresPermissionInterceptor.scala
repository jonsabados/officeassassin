package com.jshnd.shiro

import org.aopalliance.intercept.{MethodInvocation, MethodInterceptor}
import org.apache.shiro.util.ThreadContext
import java.lang.annotation.Annotation
import scala._
import scala.AnyRef
import scala.annotation.tailrec

class RequiresPermissionInterceptor extends MethodInterceptor {

  def invoke(p1: MethodInvocation): AnyRef = {
    val annotation = p1.getMethod.getAnnotation(classOf[RequiresPermission])
    val subject = ThreadContext.getSubject
    val permission = apply(annotation.value(), substitutions(p1.getArguments, p1.getMethod.getParameterAnnotations))
    subject.checkPermission(permission)
    p1.proceed()
  }

  @tailrec
  private def substitutions(args: Array[AnyRef],
                    annotations: Array[Array[Annotation]],
                    index: Int = 0,
                    accu: List[(AnyRef, Substitution)] = List()): List[(AnyRef, Substitution)] = {
    if(index >= annotations.length) accu
    else {
      val sub = annotations(index).find(a => a.annotationType().eq(classOf[Substitution]))
      substitutions(args, annotations, index + 1, addIfPresent(args(index), sub, accu))
    }
  }

  private def addIfPresent(arg: AnyRef,
                           sub: Option[Annotation],
                           accu: List[(AnyRef, Substitution)]): List[(AnyRef, Substitution)] = sub match {
    case  Some(annotation) =>  (arg, annotation.asInstanceOf[Substitution]) :: accu
    case _ => accu
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

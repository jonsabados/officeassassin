package com.jshnd.validation

import org.aopalliance.intercept.{MethodInvocation, MethodInterceptor}
import com.google.inject.Inject
import javax.validation.{ConstraintViolation, ConstraintViolationException, Valid, Validator}
import java.lang.reflect.Method

class ValidationInterceptor extends MethodInterceptor {

  @Inject
  var validator: Validator = null

  def invoke(p1: MethodInvocation): AnyRef = {
    val toValidateIndex = argWithValid(p1.getMethod)
    if(toValidateIndex == -1) p1.proceed()
    else if (argWithValid(p1.getMethod, toValidateIndex + 1) != -1)
      throw new IllegalArgumentException("Multiple @Valid annotations found - giving up")
    else doValidate(p1.getArguments()(toValidateIndex), p1)
  }

  def doValidate(arg: Any, invocation: MethodInvocation): AnyRef = {
    val result: java.util.Set[ConstraintViolation[Any]] = validator.validate(arg)
    if(!result.isEmpty) throw new ConstraintViolationException(result.asInstanceOf)
    invocation.proceed()
  }

  def argWithValid(method: Method, indexFrom: Int = 0) =
    method.getParameterAnnotations.indexWhere(p => p.find(a => a.annotationType().equals(classOf[Valid])).isDefined, indexFrom)

}

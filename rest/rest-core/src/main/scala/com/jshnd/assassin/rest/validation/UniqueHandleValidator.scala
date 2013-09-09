package com.jshnd.assassin.rest.validation

import javax.validation.{ConstraintValidatorContext, ConstraintValidator}
import com.jshnd.assassin.dto.UserDto
import javax.inject.Inject
import com.jshnd.assassin.bindings.FindUserByHandle
import com.jshnd.assassin.user.User
import com.jshnd.assassin.validation.{UniqueHandle, UniqueEmail}

class UniqueHandleValidator @Inject() (@FindUserByHandle findByHandle: (String) => Option[User])
  extends UniqueUserValidator[UniqueHandle, String] {

  var message: String = null

  val propertyName = "handle"

  def property(in: UserDto) = in.handle

  def find(prop: String) = findByHandle(prop)

  def initialize(constraintAnnotation: UniqueHandle) {
    message = constraintAnnotation.message()
  }

}

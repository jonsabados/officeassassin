package com.jshnd.assassin.rest.validation

import com.jshnd.assassin.dto.UserDto
import javax.inject.Inject
import com.jshnd.assassin.bindings.FindUserByEmail
import com.jshnd.assassin.user.User
import com.jshnd.assassin.validation.UniqueEmail

class UniqueEmailValidator @Inject() (@FindUserByEmail findByEmail: (String) => Option[User])
  extends UniqueUserValidator[UniqueEmail, String] {

  var message: String = null

  val propertyName = "emailAddress"

  def property(in: UserDto) = in.emailAddress

  def find(prop: String) = findByEmail(prop)

  def initialize(constraintAnnotation: UniqueEmail) {
    message = constraintAnnotation.message()
  }

}

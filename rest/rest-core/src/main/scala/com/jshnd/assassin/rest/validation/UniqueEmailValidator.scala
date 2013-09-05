package com.jshnd.assassin.rest.validation

import javax.validation.{ConstraintValidatorContext, ConstraintValidator}
import com.jshnd.assassin.dto.UserDto
import javax.inject.Inject
import com.jshnd.assassin.bindings.FindUserByEmail
import com.jshnd.assassin.user.User
import com.jshnd.assassin.validation.UniqueEmail

class UniqueEmailValidator @Inject() (@FindUserByEmail findByEmail: (String) => Option[User])
  extends ConstraintValidator[UniqueEmail, UserDto] {

  def initialize(constraintAnnotation: UniqueEmail) {}

  def isValid(in: UserDto, context: ConstraintValidatorContext): Boolean = {
    if(in.emailAddress == null) true
    else in.id match {
      case None => findByEmail(in.emailAddress).isEmpty
      case Some(editId) => findByEmail(in.emailAddress) match {
        case None => true
        case Some(User(Some(existingId), _, _, _, _)) => existingId == editId
        case _ => false
      }
    }
  }

}

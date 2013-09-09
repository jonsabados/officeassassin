package com.jshnd.assassin.rest.validation

import javax.validation.{ConstraintValidatorContext, ConstraintValidator}
import com.jshnd.assassin.dto.UserDto
import com.jshnd.assassin.user.User
import java.lang.annotation.Annotation

trait UniqueUserValidator[A <: Annotation, T] extends ConstraintValidator[A, UserDto] {

  def property(in: UserDto): T

  def propertyName: String

  def message: String

  def find(prop: T): Option[User]

  def isValid(in: UserDto, context: ConstraintValidatorContext): Boolean = {
    if(!valid(in)) {
      context.buildConstraintViolationWithTemplate(message).addNode(propertyName).addConstraintViolation()
      context.disableDefaultConstraintViolation()
      false
    } else true
  }

  private def valid(in: UserDto): Boolean = {
    if(property(in) == null) true
    else in.id match {
      case None => find(property(in)).isEmpty
      case Some(editId) => find(property(in)) match {
        case None => true
        case Some(User(Some(existingId), _, _, _, _)) => existingId == editId
        case _ => false
      }
    }
  }

}

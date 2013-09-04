package com.jshnd.assassin.rest.exceptionmapping

import scala.collection.JavaConversions._
import scala.collection.mutable.{Set => MSet}
import javax.ws.rs.ext.{Provider, ExceptionMapper}
import javax.validation.{ConstraintViolation, ConstraintViolationException}
import javax.ws.rs.core.Response
import com.jshnd.assassin.dto.{GeneralValidationFailure, FieldValidationFailure, ValidationFailures}

@Provider
class ValidationFailureExceptionMapper extends ExceptionMapper[ConstraintViolationException] {

  def toResponse(p1: ConstraintViolationException): Response =
    Response.status(Response.Status.BAD_REQUEST).entity(toFailures(p1.getConstraintViolations)).build()

  def toFailures(violations: MSet[ConstraintViolation[_]]): ValidationFailures = {
    val general = violations.filter(p => p.getPropertyPath == null).map(toGeneralValidationFailure)
    val field = violations.filter(p => p.getPropertyPath != null).map(toFieldValidationFailure)
    new ValidationFailures(general, field)
  }

  def toFieldValidationFailure(v: ConstraintViolation[_]): FieldValidationFailure =
    new FieldValidationFailure(v.getMessageTemplate, v.getMessage, v.getPropertyPath.toString, v.getInvalidValue)

  def toGeneralValidationFailure(v: ConstraintViolation[_]): GeneralValidationFailure =
    new GeneralValidationFailure(v.getMessageTemplate, v.getMessage, v.getInvalidValue)

}

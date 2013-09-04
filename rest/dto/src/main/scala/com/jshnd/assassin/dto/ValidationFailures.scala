package com.jshnd.assassin.dto

import javax.xml.bind.annotation.{XmlElement, XmlAccessType, XmlRootElement, XmlAccessorType}
import scala.beans.BeanProperty
import java.util.{Set => JSet}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
case class ValidationFailures(@xmlElementWrapper(name = "generalFailures")
                              @xmlElement(name = "failure")
                              @BeanProperty
                              generalFailures: JSet[GeneralValidationFailure],

                              @xmlElementWrapper(name = "fieldFailures")
                              @xmlElement(name = "failure")
                              @BeanProperty
                              fieldFailures: JSet[FieldValidationFailure]) {

  def this() = this(null, null)

}

trait ValidationFailure {
  def code: String
  def message: String
  def rejectedValue: Any
}

@XmlElement
@XmlAccessorType(XmlAccessType.FIELD)
case class GeneralValidationFailure(@xmlElement @BeanProperty code: String,
                                    @xmlElement @BeanProperty message: String,
                                    @xmlElement @BeanProperty rejectedValue: AnyRef) extends ValidationFailure {

  def this() = this(null, null, null)

}

@XmlElement
@XmlAccessorType(XmlAccessType.FIELD)
case class FieldValidationFailure(@xmlElement @BeanProperty code: String,
                                  @xmlElement @BeanProperty message: String,
                                  @xmlElement @BeanProperty field: String,
                                  @xmlElement @BeanProperty rejectedValue: AnyRef)  extends ValidationFailure {

  def this() = this(null, null, null, null)

}
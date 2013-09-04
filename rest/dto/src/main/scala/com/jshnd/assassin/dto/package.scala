package com.jshnd.assassin

import javax.xml.bind.annotation.{XmlElementWrapper, XmlElement}
import scala.annotation.meta.field
import javax.validation.constraints._
import org.apache.bval.constraints._

package object dto {

  type xmlElement = XmlElement @field
  type xmlElementWrapper = XmlElementWrapper @field

  type notNull = NotNull @field
  type size = Size @field
  type email = Email @field

}

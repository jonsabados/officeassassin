package com.jshnd.assassin

import javax.xml.bind.annotation.{XmlElementWrapper, XmlElement}
import scala.annotation.meta.field
import javax.validation.constraints._
import org.apache.bval.constraints._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}

package object dto {

  type xmlElement = XmlElement @field
  type xmlElementWrapper = XmlElementWrapper @field
  type xmlTypeAdapter = XmlJavaTypeAdapter @field

  type notNull = NotNull @field
  type size = Size @field
  type email = Email @field

  class IntOptionAdapter extends XmlAdapter[Integer, Option[Int]] {
    def unmarshal(p1: Integer): Option[Int] = if(p1 == null) None else Some(p1)

    def marshal(p1: Option[Int]): Integer = p1 match {
      case Some(int) => int
      case None => null
    }
  }

}

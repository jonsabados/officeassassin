package com.jshnd.assassin

import javax.xml.bind.annotation.{XmlElementWrapper, XmlElement}
import scala.annotation.meta.field
import javax.validation.constraints._
import org.apache.bval.constraints._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.{SerializerProvider, JsonSerializer}
import com.fasterxml.jackson.core.JsonGenerator

package object dto {

  type xmlElement = XmlElement @field
  type xmlElementWrapper = XmlElementWrapper @field
  type xmlTypeAdapter = XmlJavaTypeAdapter @field

  type jsonSerialize = JsonSerialize @field

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

  class OptionSerializerInt extends JsonSerializer[Option[Int]] {
    def serialize(p1: Option[Int], p2: JsonGenerator, p3: SerializerProvider) {
      p1 match {
        case Some(int) => p2.writeNumber(p1.get)
        case None => p2.writeNull()
      }
    }
  }

}

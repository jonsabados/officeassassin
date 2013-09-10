package com.jshnd.assassin.dto

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}
import scala.beans.BeanProperty
import com.jshnd.assassin.validation.{UniqueHandle, UniqueEmail}


trait UserDto {
  def id: Option[Int]
  def emailAddress: String
  def handle: String
  def fullName: String
}

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
case class UserViewDto(@BeanProperty @xmlElement
                       @xmlTypeAdapter(classOf[IntOptionAdapter])
                       @jsonSerialize(using = classOf[OptionSerializerInt]) var id: Option[Int],
                       @BeanProperty @xmlElement var emailAddress: String,
                       @BeanProperty @xmlElement var handle: String,
                       @BeanProperty @xmlElement var fullName: String)
  extends UserDto {

  def this() = this(None, null, null, null)
}

@UniqueEmail
@UniqueHandle
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
case class UserCreateDto(@notNull
                         @email
                         @size(min = 1, max = 255)
                         @xmlElement
                         @BeanProperty
                         var emailAddress: String,

                         @notNull
                         @size(min = 1, max = 64)
                         @xmlElement
                         @BeanProperty
                         var handle: String,

                         @size(min = 1, max = 255)
                         @xmlElement
                         @BeanProperty
                         var fullName: String,

                         @notNull
                         @xmlElement
                         @BeanProperty
                         var password: String)
  extends UserDto {

  def this() = this(null, null, null, null)

  def id = None

}

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
// TODO - figure out how to bind id via a path param with a post body
case class UserEditDto(@BeanProperty @xmlElement @xmlTypeAdapter(classOf[IntOptionAdapter]) var id: Option[Int],
                       @BeanProperty @xmlElement var emailAddress: String,
                       @BeanProperty @xmlElement var handle: String,
                       @BeanProperty @xmlElement var fullName: String,
                       @BeanProperty @xmlElement var password: String)
  extends UserDto {

  def this() = this(None, null, null, null, null)

}

package com.jshnd.assassin.dto

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}
import scala.beans.BeanProperty


abstract class UserBaseDto(emailAddress: String, handle: String, fullName: String)

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
case class UserViewDto(@BeanProperty @xmlElement var id: Int,
                       @BeanProperty @xmlElement var emailAddress: String,
                       @BeanProperty @xmlElement var handle: String,
                       @BeanProperty @xmlElement var fullName: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(0, null, null, null)
}

@XmlRootElement
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
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(null, null, null, null)

}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
// TODO - figure out how to bind id via a path param with a post body
case class UserEditDto(@BeanProperty @xmlElement var id: Int,
                       @BeanProperty @xmlElement var emailAddress: String,
                       @BeanProperty @xmlElement var handle: String,
                       @BeanProperty @xmlElement var fullName: String,
                       @BeanProperty @xmlElement var password: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(-1, null, null, null, null)

}

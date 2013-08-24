package com.jshnd.assassin.dto

import javax.xml.bind.annotation.{XmlElement, XmlRootElement}
import javax.validation.constraints._
import org.apache.bval.constraints._
import scala.beans.BeanProperty


abstract class UserBaseDto(emailAddress: String, handle: String, fullName: String)

@XmlRootElement
case class UserViewDto(@BeanProperty @XmlElement var id: Int,
                       @BeanProperty @XmlElement var emailAddress: String,
                       @BeanProperty @XmlElement var handle: String,
                       @BeanProperty @XmlElement var fullName: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(0, null, null, null)
}

@XmlRootElement
case class UserCreateDto(@NotNull
                         @Email
                         @Size(min = 1, max = 255)
                         @BeanProperty @XmlElement var emailAddress: String,
                         @NotNull
                         @Size(min = 1, max = 64)
                         @BeanProperty @XmlElement var handle: String,
                         @Size(min = 1, max = 255)
                         @BeanProperty @XmlElement var fullName: String,
                         @NotNull
                         @BeanProperty @XmlElement var password: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(null, null, null, null)

}

@XmlRootElement
// TODO - figure out how to bind id via a path param with a post body
case class UserEditDto(@BeanProperty @XmlElement var id: Int,
                       @BeanProperty @XmlElement var emailAddress: String,
                       @BeanProperty @XmlElement var handle: String,
                       @BeanProperty @XmlElement var fullName: String,
                       @BeanProperty @XmlElement var password: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(-1, null, null, null, null)

}

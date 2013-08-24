package com.jshnd.assassin.dto

import javax.xml.bind.annotation.{XmlElement, XmlRootElement}
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
case class UserCreateDto(@BeanProperty @XmlElement var emailAddress: String,
                         @BeanProperty @XmlElement var handle: String,
                         @BeanProperty @XmlElement var fullName: String,
                         @BeanProperty @XmlElement var password: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(null, null, null, null)

}

@XmlRootElement
case class UserEditDto(@BeanProperty @XmlElement var emailAddress: String,
                       @BeanProperty @XmlElement var handle: String,
                       @BeanProperty @XmlElement var fullName: String,
                       @BeanProperty @XmlElement var password: String)
  extends UserBaseDto(emailAddress, handle, fullName) {

  def this() = this(null, null, null, null)

}

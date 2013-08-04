package com.j

import com.sun.xml.internal.txw2.annotation.XmlElement
import javax.xml.bind.annotation.{XmlRootElement}
import scala.annotation.meta.field
import scala.beans.BeanProperty

@XmlRootElement(name = "User")
class UserBaseDto(@BeanProperty @(XmlElement @field) var emailAddress: String,
                  @BeanProperty @(XmlElement @field) var handle: String,
                  @BeanProperty @(XmlElement @field) var fullName: String) {

}

@XmlRootElement(name = "UserCreate")
class UserCreateDto(emailAddress: String,
                    handle: String,
                    fullName: String,
                    @BeanProperty @(XmlElement @field) var password: String)
  extends UserBaseDto(emailAddress = emailAddress, handle = handle, fullName = fullName) {

}

@XmlRootElement(name = "UserEdit")
class UserEditDto(@BeanProperty @(XmlElement @field) var id: Int,
                  emailAddress: String,
                  handle: String,
                  fullName: String,
                  @BeanProperty @(XmlElement @field) var password: String)
  extends UserBaseDto(emailAddress = emailAddress, handle = handle, fullName = fullName) {

}

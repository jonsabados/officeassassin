package com.j

import com.sun.xml.internal.txw2.annotation.XmlElement
import javax.xml.bind.annotation.{XmlAccessorType, XmlAccessType, XmlRootElement}

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
class UserBaseDto(@XmlElement var emailAddress: String,
                  @XmlElement var handle: String,
                  @XmlElement var fullName: String) {

}

@XmlRootElement(name = "UserCreate")
class UserCreateDto(emailAddress: String,
                    handle: String,
                    fullName: String,
                    @XmlElement var password: String)
  extends UserBaseDto(emailAddress = emailAddress, handle = handle, fullName = fullName) {

}

@XmlRootElement(name = "UserEdit")
class UserEditDto(@XmlElement var id: Int,
                  emailAddress: String,
                  handle: String,
                  fullName: String,
                  @XmlElement var password: String)
  extends UserBaseDto(emailAddress = emailAddress, handle = handle, fullName = fullName) {

}

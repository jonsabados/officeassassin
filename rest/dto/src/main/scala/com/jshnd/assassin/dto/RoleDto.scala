package com.jshnd.assassin.dto

import javax.xml.bind.annotation.{XmlAccessType, XmlAccessorType, XmlRootElement}
import scala.beans.BeanProperty

@XmlRootElement(name = "role")
@XmlAccessorType(XmlAccessType.FIELD)
case class RoleDto(@BeanProperty @xmlElement var name: String)


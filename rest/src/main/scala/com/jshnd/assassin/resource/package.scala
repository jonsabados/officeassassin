package com.jshnd.assassin

import javax.xml.bind.annotation.{XmlElementWrapper, XmlElement}
import scala.annotation.meta.{field, getter}

package object resource {

  type xmlElement =  XmlElement @field

  type xmlElementWrapper = XmlElementWrapper @field

}

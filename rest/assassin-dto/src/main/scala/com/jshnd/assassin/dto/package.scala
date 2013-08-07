package com.jshnd.assassin

import javax.xml.bind.annotation.{XmlElementWrapper, XmlElement}
import scala.annotation.meta.field

package object dto {

  type xmlElement = XmlElement @field

  type xmlElementWrapper = XmlElementWrapper @field

}

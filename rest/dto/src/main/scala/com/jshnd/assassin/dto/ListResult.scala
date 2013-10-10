package com.jshnd.assassin.dto

import java.util.{List => JList}
import javax.xml.bind.annotation._
import scala.beans.BeanProperty


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Array(classOf[RoleDto]))
case class ListResult[T](@xmlElementWrapper(name = "items")
                         @xmlElement(name = "data")
                         @BeanProperty
                         var data: JList[T],

                         @xmlElement
                         @BeanProperty
                         var offset: Int,

                         @xmlElement
                         @BeanProperty
                         var pageLength: Int,

                         @xmlElement
                         @BeanProperty
                         var totalRecords: Int) {

  def this() = this(null, 0, 0, 0)

}



package com.jshnd.assassin.dto

import scala.collection.JavaConversions._
import java.util.{List => JList}
import javax.xml.bind.annotation._
import scala.beans.BeanProperty


object ListResult {
  implicit def wrapList[T](scalaList: List[T]):ListResult[T] = new ListResult(scalaList, scalaList.length)
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(Array(classOf[UserViewDto]))
case class ListResult[T](@xmlElementWrapper(name = "items")
                         @xmlElement(name = "data")
                         @BeanProperty
                         var data: JList[T],

                         @xmlElement
                         @BeanProperty
                         var totalRecords: Int) {

  def this() = this(null, 0)

}



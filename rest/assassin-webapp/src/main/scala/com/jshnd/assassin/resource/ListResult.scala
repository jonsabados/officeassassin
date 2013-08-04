package com.jshnd.assassin.resource

import scala.collection.JavaConversions._
import java.util.{List => JList}
import javax.xml.bind.annotation._
import scala.beans.BeanProperty


object ListResult {
  implicit def wrapList[T](scalaList: List[T]):ListResult[T] = new ListResult(scalaList, scalaList.length)
}

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
class ListResult[T](@BeanProperty
                    @xmlElementWrapper(name = "items")
                    @xmlElement(name = "item") var data: JList[T],
                    @BeanProperty
                    @xmlElement var totalRecords: Int) {

  private def this() = this(List[T](), 0)

}


package com.jshnd.assassin.dto

import scala.collection.JavaConversions._
import java.util.{List => JList}
import javax.xml.bind.annotation._
import scala.beans.BeanProperty
import scala.annotation.meta.field


object ListResult {
  implicit def wrapList[T](scalaList: List[T]):ListResult[T] = new ListResult(scalaList, scalaList.length)
}

@XmlRootElement
case class ListResult[T](@xmlElementWrapper(name = "items")
                         @xmlElement @BeanProperty var data: JList[T],
                         @xmlElement @BeanProperty var totalRecords: Int) {


}


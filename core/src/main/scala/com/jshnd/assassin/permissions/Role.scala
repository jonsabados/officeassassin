package com.jshnd.assassin.permissions

import com.jshnd.assassin.HasName
import org.squeryl.KeyedEntity
import org.squeryl.annotations._

case class Role(@Column("name") name: String) extends KeyedEntity[Int] with HasName {
  var id: Int = _
}

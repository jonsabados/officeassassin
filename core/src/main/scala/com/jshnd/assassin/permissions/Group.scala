package com.jshnd.assassin.permissions

import com.jshnd.assassin.HasName
import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column

case class Group(@Column("name") name: String) extends HasName with KeyedEntity[Int] {
   var id: Int = _
}
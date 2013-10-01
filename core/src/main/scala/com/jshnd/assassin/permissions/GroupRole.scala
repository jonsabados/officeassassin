package com.jshnd.assassin.permissions

import org.squeryl.KeyedEntity
import org.squeryl.annotations._

case class GroupRole(@Column("group_id")
                     groupId: Int,
                     @Column("role_id")
                     roleId: Int) extends KeyedEntity[Int] {
  var id: Int = _
}

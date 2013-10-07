package com.jshnd.assassin.permissions

import org.squeryl.KeyedEntity
import org.squeryl.annotations._

case class UserGroup(@Column("user_id")
                    userId: Int,
                    @Column("group_id")
                    groupId: Int) extends KeyedEntity[Int] {
  var id: Int = _
}

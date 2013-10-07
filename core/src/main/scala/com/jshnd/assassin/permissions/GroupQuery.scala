package com.jshnd.assassin.permissions

import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Query
import com.jshnd.assassin.persistence.AssassinQuery
import com.jshnd.assassin.AssassinSchema._

case class GroupQuery(groupName: Option[String]) extends AssassinQuery[Group] {
  def query: Query[Group] = from(groups)(g =>
    where(g.name === groupName.?)
    select g
  )
}

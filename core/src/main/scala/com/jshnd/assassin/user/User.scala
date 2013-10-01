package com.jshnd.assassin.user

import org.squeryl.KeyedEntity
import org.squeryl.annotations.Column

case class User(@Column("email_address")
                emailAddress: String,
                @Column("handle")
                handle: String,
                @Column("full_name")
                fullName: Option[String],
                @Column("password_hash")
                passwordHash: String) extends KeyedEntity[Int] {

  def this() = this("", "", Some(""), "")

  var id: Int = _

}

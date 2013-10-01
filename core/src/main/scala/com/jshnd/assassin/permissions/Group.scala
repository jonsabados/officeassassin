package com.jshnd.assassin.permissions

import com.jshnd.assassin.HasName

case class Group(id: Option[Int], name: String) extends HasName
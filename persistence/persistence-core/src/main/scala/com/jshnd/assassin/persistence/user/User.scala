package com.jshnd.assassin.persistence.user

case class User(id: Option[Int], emailAddress: String, handle: String, fullName: Option[String], passwordHash: String) {

}

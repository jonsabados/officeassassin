package com.jshnd.assassin.persistence.user

case class User(id: Int, emailAddress: String, handle: String, fullName: Option[String], passwordHash: String) {

}

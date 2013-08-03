package com.jshnd.assassin.persistence.jpa

import javax.persistence._

@Entity
@Table(name = "user")
@Access(AccessType.FIELD)
class UserEntity {

  def this(emailAddress: String, handle: String, fullName: Option[String], passwordHash: String) {
    this()
    this.emailAddress = emailAddress
    this.handle = handle
    this.fullName = fullName
    this.passwordHash = passwordHash
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Int = _

  @Column(name = "email_address")
  var emailAddress: String = _

  @Column(name = "handle")
  var handle: String = _

  @Column(name = "full_name")
  var fullName: Option[String] = None

  @Column(name = "password_hash")
  var passwordHash: String = _

}

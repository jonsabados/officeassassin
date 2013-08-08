package com.jshnd.assassin.jpa

import javax.persistence._

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
class UserEntity {

  def this(emailAddress: String, handle: String, fullName: Option[String], passwordHash: String) {
    this()
    this.emailAddress = emailAddress
    this.handle = handle
    if(fullName.isDefined) this.fullName = fullName.get
    this.passwordHash = passwordHash
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Integer = _

  @Column(name = "email_address")
  var emailAddress: String = _

  @Column(name = "handle")
  var handle: String = _

  @Column(name = "full_name")
  var fullName: String = _

  @Column(name = "password_hash")
  var passwordHash: String = _

}

package com.jshnd.assassin.persistence.jpa

import javax.persistence._

@Entity
@Table(name = "users")
@Access(AccessType.FIELD)
class UserEntity {

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

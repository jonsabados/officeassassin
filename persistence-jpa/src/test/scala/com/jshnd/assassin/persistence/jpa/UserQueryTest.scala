package com.jshnd.assassin.persistence.jpa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import com.google.inject.Guice
import com.google.inject.persist.PersistService
import com.jshnd.assassin.user.{UserQuery, User}

@RunWith(classOf[JUnitRunner])
class UserQueryTest extends FunSpec with QueryTester {

  testStore.persist(new User(None, "foo@foo.com", "Foo Foe", Some("Buis"), "123"))
  testStore.persist(new User(None, "bar@foo.com", "Bar Bing", None, "123"))

  it("Should find users by email") {
    assert(testStore.find(new UserQuery(Some("foo@foo.com"))) ===
      List(User(Some(1), "foo@foo.com", "Foo Foe", Some("Buis"), "123")))
  }

  it("Should find users by full name") {
    assert(testStore.find(new UserQuery(None, Some("Buis"))) ===
      List(User(Some(1), "foo@foo.com", "Foo Foe", Some("Buis"), "123")))
  }

  it("Should not find users when full name does not match") {
    assert(testStore.find(new UserQuery(Some("foo@foo.com"), Some("Bar Bing"))) === List())
  }

  it("Should find users without constraints") {
    assert(testStore.find(new UserQuery()) ===
      List(
        User(Some(1), "foo@foo.com", "Foo Foe", Some("Buis"), "123"),
        User(Some(2), "bar@foo.com", "Bar Bing", None, "123")
      )
    )
  }

  it("Should respect limits") {
    val query = new UserQuery()
    query.firstRecord = 0
    query.lastRecord = 1
    assert(testStore.find(query) ===
      List(User(Some(1), "foo@foo.com", "Foo Foe", Some("Buis"), "123"))
    )
  }

}

package com.jshnd.assassin.jpa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import com.jshnd.assassin.user.{UserQuery, User}
import org.apache.commons.codec.digest.DigestUtils.sha256Hex

@RunWith(classOf[JUnitRunner])
class UserQueryExecutionTest extends FunSpec with QueryTester {
  //TODO - use dbunit or something - this is fragile if base schema stuff adds users etc.
  val pwHash = sha256Hex("123")

  testStore.persist(new User(None, "foo@foo.com", "Foo Foe", Some("Buis"), pwHash))
  testStore.persist(new User(None, "bar@foo.com", "Bar Bing", None, pwHash))

  describe("UserQuery") {
    it("Should find users by email") {
      assert(testStore.find(new UserQuery(Some("foo@foo.com"))) ===
      List(User(Some(2), "foo@foo.com", "Foo Foe", Some("Buis"), pwHash)))
    }

    it("Should find users by handle") {
      assert(testStore.find(new UserQuery(None, Some("Foo Foe"))) ===
        List(User(Some(2), "foo@foo.com", "Foo Foe", Some("Buis"), pwHash)))
    }

    it("Should not find users when full name does not match") {
      assert(testStore.find(new UserQuery(Some("foo@foo.com"), Some("Bar Bing"))) === List())
    }

    it("Should find users without constraints") {
      assert(testStore.find(new UserQuery()) ===
        List(
          User(Some(1), "admin", "delete_me", None, sha256Hex("adminchangeme")),
          User(Some(2), "foo@foo.com", "Foo Foe", Some("Buis"), pwHash),
          User(Some(3), "bar@foo.com", "Bar Bing", None, pwHash)
        )
      )
    }

    it("Should respect limits") {
      val query = new UserQuery()
      query.firstRecord = 0
      query.lastRecord = 2
      assert(testStore.find(query) ===
        List(
          User(Some(1), "admin", "delete_me", None, sha256Hex("adminchangeme")),
          User(Some(2), "foo@foo.com", "Foo Foe", Some("Buis"), pwHash)
        )
      )
    }
  }

}

package com.jshnd.assassin.user

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import com.jshnd.assassin.DbTester
import com.jshnd.assassin.persistence.AssassinStore

@RunWith(classOf[JUnitRunner])
class UserQueryTest extends FunSpec with DbTester {

  val store = injector.getInstance(classOf[AssassinStore])

  describe("UserQuery") {
    it("Should list all users when email and handle are omitted") {
      assert(2 === store.allResults(new UserQuery(None, None, 0, 0)))
    }

    it("should search by email") {
      val results = store.allResults(new UserQuery(Some("one@one.com"), None, 0, 0))
      assert(1 === results.size)
      assert(new User("one@one.com", "one", None, "123") === results.head)
    }

    it("should search by handle") {
      val results = store.allResults(new UserQuery(None, Some("two"), 0, 0))
      assert(1 === results.size)
      assert(new User("two@one.com", "two", None, "123") === results.head)
    }

    it("should search by email and handle") {
      val results = store.allResults(new UserQuery(Some("one@one.com"), Some("two"), 0, 0))
      assert(0 === results.size)
    }
  }

  def testData: String = "com/jshnd/assassin/user/UserQueryTest.xml"
}

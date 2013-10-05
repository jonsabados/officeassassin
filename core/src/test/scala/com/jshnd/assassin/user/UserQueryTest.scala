package com.jshnd.assassin.user

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Inside, FunSpec}
import com.jshnd.assassin.DbTester
import com.jshnd.assassin.persistence.{QueryResult, AssassinStore}

@RunWith(classOf[JUnitRunner])
class UserQueryTest extends FunSpec with Inside with DbTester {

  val store = injector.getInstance(classOf[AssassinStore])

  describe("UserQuery") {
    it("Should list all users when email and handle are omitted") {
      assert(2 === store.allResults(new UserQuery(None, None, 0, 0)).resultCount)
    }

    it("should search by email") {
      inside(store.allResults(new UserQuery(Some("one@one.com"), None, 0, 0))) {
        case QueryResult(List(User("one@one.com", "one", None, _)), 1) =>
      }
    }

    it("should search by handle") {
      inside(store.allResults(new UserQuery(None, Some("two"), 0, 0))) {
        case QueryResult(List(User("two@one.com", "two", None, _)), 1) =>
      }
    }

    it("should search by email and handle") {
      inside(store.allResults(new UserQuery(Some("one@one.com"), Some("two"), 0, 0))) {
        case QueryResult(List(), 0) =>
      }
    }
  }

  def testData: String = "/com/jshnd/assassin/user/UserQueryTest.xml"
}

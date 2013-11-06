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
    it("Should find users by email like") {
      val result = store.pagedResult(new UserQuery(Some("%foo.com"), None, None, 0, 10))
      inside(result.results) {
        case User("fooA@foo.com", _, _, _)
          :: User("fooB@foo.com", _, _, _)
          :: Nil =>
      }
    }

    it("Should find users by handle like") {
      val result = store.pagedResult(new UserQuery(None, Some("foo_"), None, 0, 10))
      inside(result.results) {
        case User(_, "fooA", _, _)
          :: User(_, "fooB", _, _)
          :: Nil =>
      }
    }

    it("Should find users by email and full name like") {
      val result = store.pagedResult(new UserQuery(None, Some("foo_"), Some("%Beta"), 0, 10))
      inside(result.results) {
        case User(_, "fooB", Some("Foo Beta"), _)
          :: Nil =>
      }
    }
  }

  def testData: String = "/com/jshnd/assassin/user/UserQueryTest.xml"
}

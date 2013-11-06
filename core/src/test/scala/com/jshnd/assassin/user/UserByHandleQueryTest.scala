package com.jshnd.assassin.user

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Inside, FunSpec}
import com.jshnd.assassin.DbTester
import com.jshnd.assassin.persistence.{QueryResult, AssassinStore}

@RunWith(classOf[JUnitRunner])
class UserByHandleQueryTest extends FunSpec with Inside with DbTester {

  val store = injector.getInstance(classOf[AssassinStore])

  describe("UserByHandleQuery") {
    it("should find users by email") {
      inside(store.allResults(new UserByHandleQuery("one"))) {
        case QueryResult(List(User("one@one.com", "one", _, _)), 1) =>
      }
    }
  }

  def testData: String = "/com/jshnd/assassin/user/UserQueryTest.xml"
}

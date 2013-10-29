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
    it("Should find users") {
      // TODO - when the query has predicates
    }
  }

  def testData: String = "/com/jshnd/assassin/user/UserQueryTest.xml"
}

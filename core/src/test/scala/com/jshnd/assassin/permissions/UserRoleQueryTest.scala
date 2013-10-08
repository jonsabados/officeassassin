package com.jshnd.assassin.permissions

import com.jshnd.assassin.DbTester
import org.scalatest.FunSpec
import com.jshnd.assassin.persistence.AssassinStore
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class UserRoleQueryTest extends FunSpec with DbTester {

  describe("UserRoleQuery") {

    val store = injector.getInstance(classOf[AssassinStore])

    it("Should find all roles a user has") {
      val roleResult = store.allResults(new UserRoleQuery("targetUser@test.com"))
      assert(roleResult.resultCount === 2)
      assert(roleResult.results.toSet === Set(
        Role("roleA"),
        Role("roleB")
      ))
    }

  }

  def testData: String = "/com/jshnd/assassin/permissions/UserRoleQueryTest.xml"
}

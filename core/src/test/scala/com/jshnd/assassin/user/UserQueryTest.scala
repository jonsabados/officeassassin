package com.jshnd.assassin.user

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Inside, FunSpec}
import com.jshnd.assassin.query.{AndPredicate, NoPredicate, FieldEqualsPredicate, AssassinQueryPredicate}

@RunWith(classOf[JUnitRunner])
class UserQueryTest extends FunSpec with Inside {

  describe("UserQuery") {

    it("Should be for users") {
      assert(new UserQuery().forType === classOf[User])
    }

    def verifyFieldPredicate(predicate: AssassinQueryPredicate, expectedField: String, expectedValue: String) {
      inside(predicate) {
        case FieldEqualsPredicate(field, value) =>
          assert(field === expectedField)
          assert(value === expectedValue)
      }
    }

    it("Should generate an empty predicate for blank searches") {
      val predicate: AssassinQueryPredicate = new UserQuery().predicate
      inside(predicate) {
        case NoPredicate() =>
      }
    }

    it("Should generate one predicate for email searches") {
      val predicate: AssassinQueryPredicate = new UserQuery(Some("foo@bar.com")).predicate
      verifyFieldPredicate(predicate, "emailAddress", "foo@bar.com")
    }

    it("Should generate one predicate for handle searches") {
      val predicate: AssassinQueryPredicate = new UserQuery(None, Some("doe")).predicate
      verifyFieldPredicate(predicate, "handle", "doe")
    }

    it("Should generate an and predicate for multiple params") {
      val predicate: AssassinQueryPredicate = new UserQuery(Some("foo@bar.com"), Some("doe")).predicate
      inside(predicate) {
        case AndPredicate(left, right) =>
          verifyFieldPredicate(left, "emailAddress", "foo@bar.com")
          verifyFieldPredicate(right, "handle", "doe")
      }
    }

  }

}

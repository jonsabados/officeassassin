package com.jshnd.assassin.rest.validation

import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import javax.validation.ConstraintValidatorContext
import com.jshnd.assassin.dto.{UserEditDto, UserCreateDto}
import com.jshnd.assassin.user.User

@RunWith(classOf[JUnitRunner])
class UniqueEmailValidatorTest extends FunSpec with MockitoSugar {

  val ctx = mock[ConstraintValidatorContext]

  def testFunc(emailExpected: String, toReturn: Option[User])(email: String): Option[User] = {
    assert(emailExpected === email)
    toReturn
  }

  describe("UniqueEmailValidator") {
    it("Should return true if a user does not have an email") {
      val input = new UserCreateDto(null, "bob", "John Doe", "12345")
      assert(new UniqueEmailValidator(e => fail("Queried when input has no email")).isValid(input, ctx) === true)
    }

    it("Should shoot down duplicates for users without ids when any match is found") {
      val input = new UserCreateDto("foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(Some(1), "foo@bar.com", "joe", None, "123")
      assert(new UniqueEmailValidator(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === false)
    }

    it("Should allow non-duplicates for users without ids when no match is found") {
      val input = new UserCreateDto("foo@bar.com", "bob", "John Doe", "12345")
      assert(new UniqueEmailValidator(testFunc("foo@bar.com", None)).isValid(input, ctx) === true)
    }

    it("Should allow non-duplicates for users with ids when no match is found") {
      val input = new UserEditDto(Some(1), "foo@bar.com", "bob", "John Doe", "12345")
      assert(new UniqueEmailValidator(testFunc("foo@bar.com", None)).isValid(input, ctx) === true)
    }

    it("Should allow a duplicate with the same user id") {
      val input = new UserEditDto(Some(1), "foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(Some(1), "foo@bar.com", "joe", None, "123")
      assert(new UniqueEmailValidator(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === true)
    }

    it("Should shoot down duplicates with a different user id") {
      val input = new UserEditDto(Some(2), "foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(Some(1), "foo@bar.com", "joe", None, "123")
      assert(new UniqueEmailValidator(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === false)
    }

    it("Should shoot down things if it gets a brain dead find function that finds stuff without ids") {
      val input = new UserEditDto(Some(2), "foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(None, "foo@bar.com", "joe", None, "123")
      assert(new UniqueEmailValidator(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === false)
    }
  }

}

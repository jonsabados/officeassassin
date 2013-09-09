package com.jshnd.assassin.rest.validation

import org.scalatest.{BeforeAndAfter, FunSpec}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import javax.validation.ConstraintValidatorContext
import com.jshnd.assassin.dto.{UserEditDto, UserCreateDto}
import com.jshnd.assassin.user.User
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderDefinedContext
import com.jshnd.assassin.validation.UniqueEmail
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class UniqueEmailValidatorTest extends FunSpec with MockitoSugar with BeforeAndAfter {

  val constraint = mock[UniqueEmail]

  val ctx = mock[ConstraintValidatorContext]

  val violationBuilder = mock[ConstraintViolationBuilder]

  val nodeBuilder = mock[NodeBuilderDefinedContext]

  when(constraint.message()).thenReturn("message")
  when(ctx.buildConstraintViolationWithTemplate("message")).thenReturn(violationBuilder)
  when(violationBuilder.addNode("emailAddress")).thenReturn(nodeBuilder)

  before {
    reset(nodeBuilder)
  }

  def testFunc(emailExpected: String, toReturn: Option[User])(email: String): Option[User] = {
    assert(emailExpected === email)
    toReturn
  }

  describe("UniqueEmailValidator") {
    def testObj(x: (String) => Option[User]): UniqueEmailValidator =  {
      val ret = new UniqueEmailValidator(x)
      ret.initialize(constraint)
      ret
    }

    it("Should return true if a user does not have an email") {
      val input = new UserCreateDto(null, "bob", "John Doe", "12345")
      assert(testObj(e => fail("Queried when input has no email")).isValid(input, ctx) === true)
      verifyZeroInteractions(nodeBuilder)
    }

    it("Should shoot down duplicates for users without ids when any match is found") {
      val input = new UserCreateDto("foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(Some(1), "foo@bar.com", "joe", None, "123")
      assert(testObj(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === false)
      verify(nodeBuilder).addConstraintViolation()
    }

    it("Should allow non-duplicates for users without ids when no match is found") {
      val input = new UserCreateDto("foo@bar.com", "bob", "John Doe", "12345")
      assert(testObj(testFunc("foo@bar.com", None)).isValid(input, ctx) === true)
      verifyZeroInteractions(nodeBuilder)
    }

    it("Should allow non-duplicates for users with ids when no match is found") {
      val input = new UserEditDto(Some(1), "foo@bar.com", "bob", "John Doe", "12345")
      assert(testObj(testFunc("foo@bar.com", None)).isValid(input, ctx) === true)
      verifyZeroInteractions(nodeBuilder)
    }

    it("Should allow a duplicate with the same user id") {
      val input = new UserEditDto(Some(1), "foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(Some(1), "foo@bar.com", "joe", None, "123")
      assert(testObj(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === true)
      verifyZeroInteractions(nodeBuilder)
    }

    it("Should shoot down duplicates with a different user id") {
      val input = new UserEditDto(Some(2), "foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(Some(1), "foo@bar.com", "joe", None, "123")
      assert(testObj(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === false)
      verify(nodeBuilder).addConstraintViolation()
    }

    it("Should shoot down things if it gets a brain dead find function that finds stuff without ids") {
      val input = new UserEditDto(Some(2), "foo@bar.com", "bob", "John Doe", "12345")
      val output = new User(None, "foo@bar.com", "joe", None, "123")
      assert(testObj(testFunc("foo@bar.com", Some(output))).isValid(input, ctx) === false)
      verify(nodeBuilder).addConstraintViolation()
    }
  }

}

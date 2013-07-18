package com.jshnd.assassin.user

import org.junit.runner.RunWith
import org.scalatest.FunSpec
import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UserServiceTest() extends FunSpec with MockitoSugar {

  describe("UserService") {

    val dao = mock[Dao]

    val testObj = new UserService(dao)

    it("should return all users when asked") {
      val expected = List(new User("foo", "foo full"), new User("bar", "bar full"))
      when(dao.list(classOf[User])).thenReturn(expected)
      assert(testObj.allUsers === expected)
    }

  }

}

package com.jshnd.assassin.user

import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfterEach, Inside, FunSpec}
import org.scalatest.mock.MockitoSugar
import com.jshnd.assassin._
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.persistence.AssassinStore
import com.jshnd.assassin.permissions.{UserGroup, GroupQuery, Group}
import org.mockito.{Matchers, ArgumentCaptor}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UserControllerTest extends FunSpec with MockitoSugar with Inside with BeforeAndAfterEach {

  val mockStore = mock[AssassinStore]

  val testObj = new UserController(mockStore)

  override def beforeEach() {
    reset(mockStore)
  }

  describe("UserController") {

    describe("#newUser") {
      it("should save the user") {
        val user = new User("foo@fo.com", "foo", Some("bar"), "123")
        val persisted = new User("foo@fo.com", "foo", Some("bar"), "123")
        persisted.id = 13

        val userGroup = new Group("users")
        userGroup.id = 22

        when(mockStore.save(users, user)).thenReturn(persisted)
        when(mockStore.load(GroupQuery(Some(USERS_GROUP)))).thenReturn(userGroup)

        testObj.newUser(user)
        verify(mockStore).save(users, user)
      }

      it("should assign the user to the user group") {
        val user = new User("foo@fo.com", "foo", Some("bar"), "123")
        val persisted = new User("foo@fo.com", "foo", Some("bar"), "123")
        persisted.id = 13

        val userGroup = new Group("users")
        userGroup.id = 22

        val groupCapture = ArgumentCaptor.forClass(classOf[UserGroup])

        when(mockStore.save(users, user)).thenReturn(persisted)
        when(mockStore.load(GroupQuery(Some(USERS_GROUP)))).thenReturn(userGroup)
        when(mockStore.save(Matchers.eq(userGroups), groupCapture.capture())).thenReturn(new UserGroup(-1, -1))

        testObj.newUser(user)
        inside(groupCapture.getValue) {
          case UserGroup(13, 22) =>
        }
      }
    }

  }

}

package com.jshnd.assassin.rest

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.mock.MockitoSugar
import com.jshnd.assassin.persistence.{PagedQueryResult, AssassinStore}
import org.mockito.Mockito._
import org.squeryl.Table
import com.jshnd.assassin.user.{UserQuery, User}
import scala.annotation.tailrec
import javax.servlet.http.HttpServletResponse

@RunWith(classOf[JUnitRunner])
class QueryUtilTest extends FunSpec with MockitoSugar with BeforeAndAfter {

  val mockStore = mock[AssassinStore]
  val mockTable = mock[Table[User]]
  val testObj = new QueryUtil[Int, User, User](mockStore, (u) => u, mockTable)

  def before() {
    reset(mockStore)
    reset(mockTable)
  }

  @tailrec
  private def users(count: Int, accu: List[User] = List()): List[User] = {
    if(count == 0) accu
    else users(count - 1, accu ::: List(new User()))
  }

  describe("QueryUtils") {

    describe("#listResponse()") {
      it("Should return a partial content response when there are more results") {
        val query = new UserQuery(10, 10)
        val result = new PagedQueryResult[User](users(10), 10, 21)
        when(mockStore.pagedResult(query)).thenReturn(result)
        val response = testObj.listResponse(query)
        assert(response.getStatus === HttpServletResponse.SC_PARTIAL_CONTENT)
      }

      it("Should return a 200 response when there are less results than results per page") {
        val query = new UserQuery(10, 10)
        val result = new PagedQueryResult[User](users(9), 9, 19)
        when(mockStore.pagedResult(query)).thenReturn(result)
        val response = testObj.listResponse(query)
        assert(response.getStatus === HttpServletResponse.SC_OK)
      }

      it("Should return a 200 response when on the last page and it is full but there are no more results") {
        val query = new UserQuery(10, 10)
        val result = new PagedQueryResult[User](users(10), 10, 20)
        when(mockStore.pagedResult(query)).thenReturn(result)
        val response = testObj.listResponse(query)
        assert(response.getStatus === HttpServletResponse.SC_OK)
      }
    }

  }

}

package com.jshnd.assassin.rest

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.mock.MockitoSugar
import com.jshnd.assassin.persistence.{PagedAssassinQuery, PagedQueryResult, AssassinStore}
import org.mockito.Mockito._
import org.squeryl.{Query, KeyedEntity, Table}
import scala.annotation.tailrec
import javax.servlet.http.HttpServletResponse

@RunWith(classOf[JUnitRunner])
class QueryUtilTest extends FunSpec with MockitoSugar with BeforeAndAfter {

  class TestEntity extends KeyedEntity[Int] {
    val id: Int = 1
  }

  val mockStore = mock[AssassinStore]
  val mockTable = mock[Table[TestEntity]]

  val mockQuery = new PagedAssassinQuery[TestEntity] {
    def offset: Int = 10

    def pageLength: Int = 10

    def query: Query[TestEntity] = ???
  }

  val testObj = new QueryUtil[Int, TestEntity, TestEntity](mockStore, (o) => o, mockTable)

  def before() {
    reset(mockStore)
    reset(mockTable)
  }

  @tailrec
  private def objects(count: Int, accu: List[TestEntity] = List()): List[TestEntity] = {
    if(count == 0) accu
    else objects(count - 1, accu ::: List(new TestEntity()))
  }

  describe("QueryUtils") {

    describe("#listResponse()") {
      it("Should return a partial content response when there are more results") {
        val result = new PagedQueryResult[TestEntity](objects(10), 10, 21)
        when(mockStore.pagedResult(mockQuery)).thenReturn(result)
        val response = testObj.listResponse(mockQuery)
        assert(response.getStatus === HttpServletResponse.SC_PARTIAL_CONTENT)
      }

      it("Should return a 200 response when there are less results than results per page") {
        val result = new PagedQueryResult[TestEntity](objects(9), 9, 19)
        when(mockStore.pagedResult(mockQuery)).thenReturn(result)
        val response = testObj.listResponse(mockQuery)
        assert(response.getStatus === HttpServletResponse.SC_OK)
      }

      it("Should return a 200 response when on the last page and it is full but there are no more results") {
        val result = new PagedQueryResult[TestEntity](objects(10), 10, 20)
        when(mockStore.pagedResult(mockQuery)).thenReturn(result)
        val response = testObj.listResponse(mockQuery)
        assert(response.getStatus === HttpServletResponse.SC_OK)
      }
    }

  }

}

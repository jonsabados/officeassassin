package com.jshnd.assassin.persistence

import com.jshnd.assassin.bindings.Transactional
import org.squeryl.Query
import org.squeryl.PrimitiveTypeMode._
import scala.collection.mutable.ListBuffer

class AssassinStore {

//  @Transactional
//  def load[I, T >: KeyedEntity[I]](table: Table[T], id: I): Option[T] = table.lookup(id)

  @Transactional
  def pagedResult[T](query: PagedAssassinQuery[T]): PagedQueryResult[T] = {
    val totalResults = from(query.query)(q => compute(count()))
    val queryData = allResults(query.query.page(query.offset, query.pageLength))
    new PagedQueryResult[T](queryData.toList, queryData.size, totalResults)
  }

  @Transactional
  def allResults[T](query: AssassinQuery[T]): QueryResult[T] = {
    val buffer = allResults(query.query)
    new QueryResult(buffer.toList, buffer.size)
  }

  private def allResults[T](query: Query[T]): ListBuffer[T] = new ListBuffer[T]++ query

}

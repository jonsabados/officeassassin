package com.jshnd.assassin.persistence

import com.jshnd.assassin.bindings.Transactional
import org.squeryl.{Table, KeyedEntity, Query}
import org.squeryl.PrimitiveTypeMode._
import scala.collection.mutable.ListBuffer

class AssassinStore {

  @Transactional
  def save[T](table: Table[T], instance: T): T = table.insert(instance)

  @Transactional
  def find[T <: KeyedEntity[I], I](table: Table[T], id: I): Option[T] = table.lookup(id)

  @Transactional
  def load[T <: KeyedEntity[I], I](table: Table[T], id: I): T = table.get(id)

  @Transactional
  def loadUnique[T](query: AssassinQuery[T]): T = query.query.single

  @Transactional
  def findUnique[T](query: AssassinQuery[T]): Option[T] = {
    val i = query.query.iterator
    if(i.isEmpty) None
    else {
      val r = i.next()
      // although chucking a generic runtime exception is lame, it matches what squeryl does so lets do it too for consistency
      if(i.hasNext) throw new RuntimeException("Non unique result for findUnique, query: " + query)
      Some(r)
    }
  }

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

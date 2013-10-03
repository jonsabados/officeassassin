package com.jshnd.assassin.persistence

import com.jshnd.assassin.bindings.Transactional
import org.squeryl.{KeyedEntity, Table}
import org.squeryl.PrimitiveTypeMode._
import scala.collection.mutable.{Buffer, ArrayBuffer}

class AssassinStore {

//  @Transactional
//  def load[I, T >: KeyedEntity[I]](table: Table[T], id: I): Option[T] = table.lookup(id)

  @Transactional
  def pagedResult[T](query: PagedQuery[T]): PagedQueryResult[T] = {
    val results = new ArrayBuffer[T]
    query.query.page(query.offset, query.pageLength).copyToBuffer(results)
    val c = from(query.query)(q => compute(count()))
    new PagedQueryResult[T](results, c)
  }

  @Transactional
  def allResults[T](query: Query[T]): Buffer[T] = {
    val results = new ArrayBuffer[T]
    query.query.copyToBuffer(results)
    results
  }

}

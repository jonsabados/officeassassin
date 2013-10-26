package com.jshnd.assassin.rest

import scala.collection.JavaConversions._
import javax.inject.Inject
import com.jshnd.assassin.persistence.{AssassinQuery, AssassinStore, PagedAssassinQuery}
import com.jshnd.assassin.dto.ListResult
import javax.ws.rs.core.Response
import javax.servlet.http.HttpServletResponse
import org.squeryl.{Table, KeyedEntity}

class QueryUtil[K, I <: KeyedEntity[K], O] @Inject() (store: AssassinStore, mapFunc: (I) => O, table: Table[I]) {

  def listResponse(query: PagedAssassinQuery[I]): Response = {
    val r = store.pagedResult(query)
    val responseObj =  ListResult(r.results.map(mapFunc), query.offset, query.pageLength, r.totalResults)
    val lastPage = r.resultCount < query.pageLength || query.offset + r.resultCount == r.totalResults
    if(lastPage) {
      Response.ok(responseObj).build()
    } else {
      Response.status(HttpServletResponse.SC_PARTIAL_CONTENT).entity(responseObj).build()
    }
  }

  def uniqueResponse(query: AssassinQuery[I]): Response = buildUniqueResponse(store.find(query))

  def uniqueResponse(key: K): Response = buildUniqueResponse(store.find(table, key))

  private def buildUniqueResponse(r: Option[I]): Response = r match {
    case Some(x) => Response.ok(mapFunc(x)).build()
    case None => Response.status(HttpServletResponse.SC_NOT_FOUND).build()
  }

}

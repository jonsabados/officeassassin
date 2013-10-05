package com.jshnd.assassin.persistence

case class PagedQueryResult[T](results: List[T], resultCount: Int, totalResults: Long)


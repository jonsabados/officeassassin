package com.jshnd.assassin.persistence


case class QueryResult[T](results: List[T], resultCount: Int)

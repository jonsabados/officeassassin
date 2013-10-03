package com.jshnd.assassin.persistence

import scala.collection.mutable.Buffer

class PagedQueryResult[T](results: Buffer[T], totalResults: Long)

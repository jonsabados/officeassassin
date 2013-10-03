package com.jshnd.assassin.persistence

import org.squeryl.Queryable

trait Query[T] {

  def query: Queryable[T]

}

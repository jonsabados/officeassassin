package com.jshnd.assassin.persistence

import org.squeryl.Query

trait AssassinQuery[T] {

  def query: Query[T]

}

package com.jshnd.assassin.query

abstract trait AssassinQuery[T] {

  def firstRecord: Int

  def lastRecord: Int

  def forType: Class[T]

  def predicate: AssassinQueryPredicate

}

package com.jshnd.assassin.query

trait AssassinStore {

  def find[T](query: AssassinQuery[T]): List[T]

  def findUnique[T](query: AssassinQuery[T]): Option[T]

  def persist[T](entity: T)

}

package com.jshnd.assassin.persistence

abstract trait AssassinStore {

  def find[T](query: AssassinQuery[T]): List[T]

  def persist[T](entity: T)

}

package com.jshnd.assassin.persistence

abstract trait AssassinStore {

  def find[T](query: AssassinQuery[T]): List[T]

}

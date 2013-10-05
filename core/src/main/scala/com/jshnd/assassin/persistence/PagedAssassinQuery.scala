package com.jshnd.assassin.persistence


trait PagedAssassinQuery[T] extends AssassinQuery[T] {

  def offset: Int

  def pageLength: Int

}

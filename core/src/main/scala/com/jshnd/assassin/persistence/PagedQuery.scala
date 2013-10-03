package com.jshnd.assassin.persistence


trait PagedQuery[T] extends Query[T] {

  def offset: Int

  def pageLength: Int

}

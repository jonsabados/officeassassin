package com.jshnd.assassin.user

abstract trait Dao {

  def list[T](ofType: Class[T]): List[T]

}

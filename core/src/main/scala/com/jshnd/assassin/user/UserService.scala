package com.jshnd.assassin.user

class UserService(dao: Dao) {

  def allUsers: List[User] = dao.list(classOf[User])

}

package com.jshnd.assassin.user

import javax.inject.Inject
import com.jshnd.assassin._
import com.jshnd.assassin.persistence.AssassinStore
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.bindings.Transactional
import com.jshnd.assassin.permissions.{UserGroup, GroupQuery}

class UserController @Inject() (store: AssassinStore) {

  @Transactional
  def newUser(user: User): User = {
    val newUser = store.save(users, user)
    val userGroup = store.load(new GroupQuery(Some(USERS_GROUP)))
    store.save(userGroups, new UserGroup(newUser.id, userGroup.id))
    newUser
  }

}

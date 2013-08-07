package com.jshnd.assassin.rest

import javax.ws.rs.{Produces, GET, Path}
import com.j.UserBaseDto
import com.jshnd.assassin.dto.ListResult
import com.google.inject.Inject
import com.jshnd.assassin.user.UserQuery
import com.jshnd.assassin.query.AssassinStore


@Path("/users")
class UserResource @Inject() (store: AssassinStore) {

  @GET
  @Produces(Array("application/json", "application/xml"))
  def allUsers(): ListResult[UserBaseDto] = {
     store.find(new UserQuery()).map(
       x => new UserBaseDto(x.emailAddress, x.handle, x.fullName.getOrElse(null))
     )
  }

}

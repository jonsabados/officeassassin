package com.jshnd.assassin.resource

import javax.ws.rs.{Produces, GET, Path}
import com.j.UserBaseDto

@Path("/users")
class UserResource {

  @GET
  @Produces(Array("application/json", "application/xml"))
  def allUsers(): ListResult[UserBaseDto] = {
     List(
       new UserBaseDto("foo@bar.com", "fooBeFun", null),
       new UserBaseDto("bar@bar.com", "fooBeFun", "Bar Hides Nothing")
    )
  }

}

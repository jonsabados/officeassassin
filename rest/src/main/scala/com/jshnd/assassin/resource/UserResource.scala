package com.jshnd.assassin.resource

import javax.ws.rs.{Produces, GET, Path}
import com.jshnd.assassin.user.User

@Path("/users")
class UserResource {

  @GET
  @Produces(Array("application/json", "application/xml"))
  def allUsers(): List[User] = {
     List(new User("joe@bob.com", "Joe bob"), new User("foo@bar.com", "Foo Bar!"))
  }

}

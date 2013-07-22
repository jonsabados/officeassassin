package com.jshnd.assassin.resource

import javax.ws.rs.{Produces, GET, Path}
@Path("/users")
class UserResource {

  @GET
  @Produces(Array("application/json", "application/xml"))
  def allUsers(): ListResult[String] = {
     List("ONe")
  }

}

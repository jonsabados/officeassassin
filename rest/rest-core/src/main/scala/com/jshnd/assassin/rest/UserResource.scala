package com.jshnd.assassin.rest

import javax.ws.rs.{POST, Produces, GET, Path}
import com.jshnd.assassin.dto.{UserBaseDto, ListResult}
import com.google.inject.Inject
import com.jshnd.assassin.user.{User, UserQuery}
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.SaveUser
import javax.ws.rs.core.Response
import java.net.URI
import org.apache.shiro.authz.annotation.RequiresPermissions


@Path("/users")
class UserResource @Inject() (store: AssassinStore, @SaveUser saveUser: (User) => Unit) {

  @GET
  @Produces(Array("application/json", "application/xml"))
  def allUsers(): ListResult[UserBaseDto] = {
     store.find(new UserQuery()).map(
       x => new UserBaseDto(x.emailAddress, x.handle, x.fullName.getOrElse(null))
     )
  }

  @GET
  @Path("secured")
  @RequiresPermissions(Array("helloShiro"))
  def helloSecurityWorks(): ListResult[UserBaseDto] = {
    store.find(new UserQuery()).map(
      x => new UserBaseDto(x.emailAddress, x.handle, x.fullName.getOrElse(null))
    )
  }

  @POST
  def test(): Response = {
    saveUser(new User(None, "jd@test.com", "foo", Some("John Doe"), "123"))
    Response.created(new URI("http://foo.com")).build()
  }

}

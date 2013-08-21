package com.jshnd.assassin.rest

import javax.ws.rs._
import com.jshnd.assassin.dto.{UserBaseDto, ListResult}
import com.google.inject.Inject
import com.jshnd.assassin.user.{User, UserQuery}
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.{FindUserByEmail, EnlistNewUser}
import javax.ws.rs.core.Response
import java.net.URI
import org.apache.shiro.authz.annotation.RequiresPermissions
import com.jshnd.assassin.user.User
import scala.Some
import com.jshnd.assassin.user.UserQuery


@Path("/users")
class UserResource @Inject() (store: AssassinStore, @FindUserByEmail findUser: (String) => Option[User]) {

  def dtoMap(x: User): UserBaseDto = new UserBaseDto(x.emailAddress, x.handle, x.fullName.getOrElse(null))

  @GET
  @Produces(Array("application/json", "application/xml"))
  def allUsers(): ListResult[UserBaseDto] = store.find(new UserQuery()).map(dtoMap)

  @GET
  @Path("/email/{email}")
  @Produces(Array("application/json", "application/xml"))
  def user(@PathParam("email") email: String): Response = {
    findUser(email) match {
      case Some(user) => Response.ok(dtoMap(user)).build()
      case None => Response.status(Response.Status.NOT_FOUND).build()
    }
  }

  @GET
  @Path("secured")
  @RequiresPermissions(Array("helloShiro"))
  def helloSecurityWorks(): ListResult[UserBaseDto] = {
    store.find(new UserQuery()).map(
      x => new UserBaseDto(x.emailAddress, x.handle, x.fullName.getOrElse(null))
    )
  }

}

package com.jshnd.assassin.rest

import javax.ws.rs._
import com.jshnd.assassin.dto.{UserViewDto, UserEditDto, UserDto, ListResult}
import com.google.inject.Inject
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.bindings.FindUserByEmail
import javax.ws.rs.core.{MediaType, Response}
import com.jshnd.assassin.user.User
import scala.{Array, Some}
import com.jshnd.assassin.user.UserQuery
import com.jshnd.shiro.{Substitution, RequiresPermission}
import javax.xml.bind.annotation.XmlSeeAlso


@Path("/users")
@Produces(Array("application/json", "application/xml"))
class UserResource @Inject() (store: AssassinStore, @FindUserByEmail findByEmail: (String) => Option[User]) {

  def dtoMap(x: User): UserViewDto = new UserViewDto(x.id, x.emailAddress, x.handle, x.fullName.getOrElse(null))

  @GET
  //@RequiresPermission("users:view:*")
  def allUsers(): ListResult[UserViewDto] = store.find(new UserQuery()).map(dtoMap)

  @GET
  @Path("/id/{id}")
  //@RequiresPermission("users:view:{id}")
  def user(@Substitution("id") @PathParam("id") id: Int): Response = {
    store.load(id, classOf[User]) match {
      case Some(user) => Response.ok(dtoMap(user)).build()
      case None => Response.status(Response.Status.NOT_FOUND).build()
    }
  }

  @GET
  @Path("/email/{emailAddress}")
  //@RequiresPermission("users:view:{email}") -- access validation will need to happen post lookup
  def userByEmail(@PathParam("emailAddress") emailAddress: String): Response = {
    findByEmail(emailAddress) match {
      case Some(user) => Response.ok(dtoMap(user)).build()
      case None => Response.status(Response.Status.NOT_FOUND).build()
    }
  }

  @PUT
  @Path("/id/{id}")
  @Consumes(Array(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
  //@RequiresPermission("users:edit:{id}")
  def updateUser(@Substitution("id") @PathParam("id") email: String, details: UserEditDto): Response = {
    Response.ok("WEEE!").build()
  }

}

package com.jshnd.assassin.rest

import javax.ws.rs._
import com.jshnd.assassin.dto.{UserViewDto, RoleDto, UserEditDto}
import com.google.inject.Inject
import javax.ws.rs.core.{MediaType, Response}
import com.jshnd.assassin.user.{UserByEmailQuery, UserQuery, User}
import scala.Array
import com.jshnd.shiro.{RequiresPermission, Substitution}
import com.jshnd.assassin.permissions.{UserRoleQuery, Role}


@Path("/users")
@Produces(Array("application/json", "application/xml"))
class UserResource @Inject() (userUtil: QueryUtil[Int, User, UserViewDto],
                              roleUtil: QueryUtil[Int, Role, RoleDto]) {

  @GET
  @RequiresPermission("users:view:*")
  def users(@QueryParam("offset")
            @DefaultValue("0")
            offset: Int,
            @QueryParam("pageLength")
            @DefaultValue("10")
            pageLength: Int): Response = userUtil.listResponse(new UserQuery(None, None, None, offset, pageLength))

  @GET
  @Path("/id/{id}")
  @RequiresPermission("users:view:{id}")
  def user(@Substitution("id") @PathParam("id") id: Int): Response = userUtil.uniqueResponse(id)

  @GET
  @Path("/id/{id}/roles")
  @RequiresPermission("users:viewRoles:{id}")
  def userRoles(@Substitution("id")
                @PathParam("id") id: Int,
                @QueryParam("offset")
                @DefaultValue("0")
                offset: Int,
                @QueryParam("pageLength")
                @DefaultValue("10")
                pageLength: Int): Response = roleUtil.listResponse(new UserRoleQuery(id, offset, pageLength))

  @GET
  @Path("/email/{emailAddress}")
  @RequiresPermission("users:view:{email}")
  def userByEmail(@PathParam("emailAddress") emailAddress: String): Response =
    userUtil.uniqueResponse(new UserByEmailQuery(emailAddress))

  @PUT
  @Path("/id/{id}")
  @Consumes(Array(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
  @RequiresPermission("users:edit:{id}")
  def updateUser(@Substitution("id") @PathParam("id") email: String, details: UserEditDto): Response = {
    Response.ok("WEEE!").build()
  }

}

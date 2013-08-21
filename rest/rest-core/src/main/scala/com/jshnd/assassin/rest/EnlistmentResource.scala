package com.jshnd.assassin.rest

import javax.ws.rs.{POST, Consumes, Path}
import com.jshnd.assassin.dto.UserCreateDto
import javax.ws.rs.core._
import com.google.inject.Inject
import com.jshnd.assassin.user.User
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.crypto.hash.Sha256Hash
import com.jshnd.assassin.rest.bindings.PasswordHasher
import com.jshnd.assassin.bindings.EnlistNewUser
import com.jshnd.assassin.user.User


@Path("/public/enlistment")
class EnlistmentResource @Inject() (@EnlistNewUser enlist: (User) => Unit,
                                    @PasswordHasher hash: (String, String) => String) {

  @POST
  @Consumes(Array(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML))
  def enlist(user: UserCreateDto, @Context uriInfo: UriInfo): Response = {
    // TODO (bigtime) - validation!!! (http://bval.apache.org/obtaining-a-validator.html)
    enlist(new User(None, user.emailAddress, user.handle, None, hash(user.emailAddress, user.password)))
    Response.created(UriBuilder.fromResource(classOf[UserResource]).path("email").path(user.emailAddress).build()).build()
  }

}

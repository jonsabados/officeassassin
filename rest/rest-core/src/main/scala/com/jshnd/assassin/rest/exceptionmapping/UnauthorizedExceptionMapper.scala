package com.jshnd.assassin.rest.exceptionmapping

import javax.ws.rs.ext.{ExceptionMapper, Provider}
import org.apache.shiro.authz.UnauthorizedException
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

@Provider
class UnauthorizedExceptionMapper extends ExceptionMapper[UnauthorizedException]{
  def toResponse(p1: UnauthorizedException): Response = Response.status(Status.FORBIDDEN).entity(p1.getMessage).build()
}

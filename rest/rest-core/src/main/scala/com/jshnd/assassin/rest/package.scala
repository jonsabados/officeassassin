package com.jshnd.assassin

import javax.ws.rs.Path
import javax.ws.rs.core.{Response, UriBuilder, UriInfo}

package object rest {

  def resourcePath(c: Class[_]): String =  c.getAnnotation(classOf[Path]).value()

  def pathBuilder(u: UriInfo, c: Class[_]): UriBuilder = u.getBaseUriBuilder.path(resourcePath(c))

  def created(ub: UriBuilder, args: Any*): Response = Response.created(ub.build(args)).build()

}

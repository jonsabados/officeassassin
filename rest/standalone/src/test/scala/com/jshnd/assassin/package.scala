package com.jshnd

import dispatch._, Defaults._
import org.apache.commons.codec.binary.Base64
import scala.util.parsing.json.JSON

package object assassin {

  val base64 = new Base64()

  val baseUrl = "http://localhost:8089"

  def asNewResource = as.Response[String] { response =>
    response.getHeader("Location")
  }

  def asJsonObject = as.Response[Map[String, _]] { response =>
    JSON.parseFull(response.getResponseBody).get.asInstanceOf[Map[String, _]]
  }

  def authHeader(username: String, password: String): String =
    "BasicCustom " + base64.encodeToString((username + ":" + password).getBytes)

  def enlist(email: String, handle: String, fullName: Option[String], password: String): String = {
    val json = fullName match {
      case Some(_) => "{\"fullName\":\"" + fullName.get +
        "\",\"password\":\"" + password +
        "\",\"emailAddress\":\"" + email+
        "\",\"handle\":\"" + handle + "\"}"
      case None => "{\"password\":\"" + password +
        "\",\"emailAddress\":\"" + email+
        "\",\"handle\":\"" + handle + "\"}"
    }
    val svc = url(s"$baseUrl/rest/public/enlistment")
    val post = svc.addHeader("Content-Type", "application/json") << json
    val enroll = Http(post OK asNewResource)
    enroll()
  }

}

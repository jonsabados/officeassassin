package com.jshnd.assassin.integration

import dispatch._, Defaults._
import org.apache.commons.codec.binary.Base64
import scala.util.parsing.json.JSON
import org.mortbay.jetty.Server
import org.mortbay.jetty.bio.SocketConnector
import scala.Array
import org.mortbay.jetty.webapp.WebAppContext
import java.nio.channels.Channels
import scala.sys.SystemProperties
import org.scalatest.{Suite, BeforeAndAfterAll}
import java.io.{FileOutputStream, File}

trait IntegrationTest extends Suite with BeforeAndAfterAll {

  val base64 = new Base64()

  val baseUrl = "http://localhost:8089"

  val server = new Server()

  val connector = new SocketConnector()

  connector.setPort(8089)
  server.setConnectors(Array(connector))

  val context = new WebAppContext()
  context.setDescriptor(webappDir + "/WEB-INF/web.xml")
  context.setResourceBase(webappDir)
  context.setContextPath("/")
  context.setParentLoaderPriority(true)

  server.setHandler(context)

  def asNewResource = as.Response[String] { response =>
    response.getHeader("Location")
  }

  def asResponseCode = as.Response[Int] { response =>
    response.getStatusCode
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

  def webappDir: String = {
    // There is probably a better way to do this, but it works to allow the tests to run in intellij & from maven
    val pathToProps = new File(getClass.getResource("/integration-test-assassin-config.properties").getFile)
    // resources -> test -> src -> standalone -> rest
    val restDir = pathToProps.getParentFile.getParentFile.getParentFile.getParentFile
    new File(restDir, "webapp/src/main/webapp").getAbsolutePath
  }

  override def beforeAll() {
    val propsFile = File.createTempFile("assassinTest", "properties")
    val out = new FileOutputStream(propsFile)
    val in = getClass.getResourceAsStream("/integration-test-assassin-config.properties")

    out.getChannel.transferFrom(Channels.newChannel(in), 0, Long.MaxValue)

    in.close()
    out.close()

    new SystemProperties().put("assassin.config", propsFile.getAbsolutePath)

    try {
      server.start()
    } finally {
      propsFile.delete()
    }
  }

  override def afterAll() {
    server.stop()
  }

}

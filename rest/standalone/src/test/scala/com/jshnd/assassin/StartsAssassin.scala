package com.jshnd.assassin

import org.mortbay.jetty.Server
import org.mortbay.jetty.bio.SocketConnector
import scala.Array
import org.mortbay.jetty.webapp.WebAppContext
import org.scalatest.{Suite, BeforeAndAfterAll}
import java.io._
import java.nio.channels.Channels
import scala.sys.SystemProperties

trait StartsAssassin extends Suite with BeforeAndAfterAll {

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

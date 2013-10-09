import org.mortbay.jetty.bio.SocketConnector
import org.mortbay.jetty.Server
import org.mortbay.jetty.webapp.WebAppContext
import scala.Array

/**
 * I'm too cheap to buy intellij ultimate to run a war for personal use, so this exists just for running & debugging
 * in intellij community.
 */
object RunInIntellij extends App {
  val server = new Server()
  val connector = new SocketConnector()

  connector.setPort(8080)
  server.setConnectors(Array(connector))

  val webappDir = "rest/webapp/src/main/webapp"
  val brunchDir = "rest/webapp/brunch/public"
  val context = new WebAppContext()
  context.setDescriptor(webappDir + "/WEB-INF/web.xml")
  context.setResourceBase(brunchDir)
  context.setContextPath("/")
  context.setParentLoaderPriority(true)

  server.setHandler(context)
  server.start()
  server.join()
}

package com.jshnd.assassin.rest

import org.mortbay.jetty.Server
import org.mortbay.jetty.bio.SocketConnector
import org.mortbay.jetty.webapp.WebAppContext

object AssassinStarter extends App {
  val server = new Server()
  val connector = new SocketConnector()

  // TODO - make this command line driven
  connector.setPort(8080)
  server.setConnectors(Array(connector))

  val context = new WebAppContext()
  context.setServer(server)
  context.setContextPath("/")

  val protectionDomain = AssassinStarter.getClass.getProtectionDomain
  val location = protectionDomain.getCodeSource.getLocation
  context.setWar(location.toExternalForm)

  server.addHandler(context)
  server.start()
}

package com.jshnd.assassin.rest

import scala.collection.JavaConversions._
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.{Singleton => GSingleton, Guice, Injector}
import java.util.Properties
import com.sun.jersey.guice.JerseyServletModule
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer
import com.google.inject.persist.PersistService
import com.jshnd.assassin.AssassinRootModule
import com.jshnd.assassin.jpa.JpaStoreModuleInitializer

class AssassinServletConfig extends GuiceServletContextListener {

  class ServletModule extends JerseyServletModule {
    override def configureServlets() {
      install(new AssassinRootModule(propertyFile.toMap))

      bind(classOf[UserResource]).in(classOf[GSingleton])

      serve("/rest/*").`with`(classOf[GuiceContainer])
    }
  }

  def propertyFile: Properties = {
    val props = new Properties()
    props.load(getClass().getResourceAsStream("/default-assassin-config.properties"))
    props
  }

  def getInjector: Injector = {
    val i = Guice.createInjector(new ServletModule)
    i.getInstance(classOf[JpaStoreModuleInitializer]).start()
    i
  }
}

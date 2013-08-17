package com.jshnd.assassin.rest

import scala.collection.JavaConversions._
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.{Singleton => GSingleton, Guice, Injector}
import java.util.Properties
import com.sun.jersey.guice.JerseyServletModule
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer
import com.jshnd.assassin.AssassinRootModule
import com.jshnd.assassin.jpa.JpaStoreModuleInitializer
import org.apache.shiro.guice.aop.ShiroAopModule
import org.apache.shiro.guice.web.ShiroWebModule
import javax.servlet.{ServletContextEvent, ServletContext}
import com.jshnd.shiro.{AuthenticationInfoSource, AuthorizationInfoSource, InjectionRealm}
import org.apache.shiro.authc.credential.{HashedCredentialsMatcher, CredentialsMatcher}
import org.apache.shiro.crypto.hash.Sha256Hash

class AssassinServletConfig extends GuiceServletContextListener {

  // this just feels kinda wrong but guice seems to think the servlet context should be private and this works...
  var sc: ServletContext = _

  class SecurityModule extends ShiroWebModule(sc) {
    def configureShiroWeb() {
      bindRealm().to(classOf[InjectionRealm])
      bind(classOf[AuthorizationInfoSource]).to(classOf[AssassinAuthorizationSource])
      bind(classOf[AuthenticationInfoSource]).to(classOf[AssassinAuthenticationSource])
      bind(classOf[CredentialsMatcher]).toInstance(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME))

      addFilterChain("/rest/**", ShiroWebModule.NO_SESSION_CREATION)
      addFilterChain("/rest/**", ShiroWebModule.AUTHC_BASIC)
    }
  }

  class ServletModule extends JerseyServletModule {
    override def configureServlets() {
      install(new AssassinRootModule(propertyFile.toMap))

      // TODO - coming from spring singleton seems right but guice seems to discourage that, look into it.
      bind(classOf[UserResource]).in(classOf[GSingleton])

      serve("/rest/*").`with`(classOf[GuiceContainer])
    }
  }

  def propertyFile: Properties = {
    val props = new Properties()
    props.load(getClass.getResourceAsStream("/default-assassin-config.properties"))
    props
  }

  def getInjector: Injector = {
    val i = Guice.createInjector(new SecurityModule, new ShiroAopModule, new ServletModule)
    i.getInstance(classOf[JpaStoreModuleInitializer]).start()
    i
  }

  override def contextInitialized(servletContextEvent: ServletContextEvent) {
    sc = servletContextEvent.getServletContext
    super.contextInitialized(servletContextEvent)
  }
}

package com.jshnd.assassin.rest

import scala.collection.JavaConversions._
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.{Singleton => GSingleton, TypeLiteral, Guice, Injector}
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
import org.apache.shiro.SecurityUtils
import com.jshnd.assassin.rest.bindings.PasswordHasher

class AssassinServletConfig extends GuiceServletContextListener {

  // this just feels kinda wrong but guice seems to think the servlet context should be private and this works...
  var sc: ServletContext = _

  class SecurityModule extends ShiroWebModule(sc) {
    def configureShiroWeb() {
      bindRealm().to(classOf[InjectionRealm])
      bind(classOf[AuthorizationInfoSource]).to(classOf[AssassinAuthorizationSource])
      bind(classOf[AuthenticationInfoSource]).to(classOf[AssassinAuthenticationSource])
      bind(classOf[CredentialsMatcher]).toInstance(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME))


      addFilterChain("/rest/public/**", ShiroWebModule.ANON)
      addFilterChain("/rest/**", ShiroWebModule.NO_SESSION_CREATION)
      addFilterChain("/rest/**", ShiroWebModule.AUTHC_BASIC)
    }

  }

  class ServletModule extends JerseyServletModule {
    override def configureServlets() {
      install(new AssassinRootModule(propertyFile.toMap))

      bind(classOf[UserResource]).in(classOf[GSingleton])
      bind(classOf[EnlistmentResource]).in(classOf[GSingleton])

      // TODO - doesn't belong here, but blows up if its in the security module, find out why
      bind(new TypeLiteral[(String, String) => String] {}).annotatedWith(classOf[PasswordHasher]).toInstance(hashPassword)

      serve("/rest/*").`with`(classOf[GuiceContainer])
      ShiroWebModule.bindGuiceFilter(binder())
    }

    def hashPassword(email: String, password: String): String = new Sha256Hash(password, email).toHex
  }

  def propertyFile: Properties = {
    val props = new Properties()
    props.load(getClass.getResourceAsStream("/default-assassin-config.properties"))
    props
  }

  def getInjector: Injector = {
    val i = Guice.createInjector(new SecurityModule, new ShiroAopModule, new ServletModule)
    i.getInstance(classOf[JpaStoreModuleInitializer]).start()
    val securityManager = i.getInstance(classOf[org.apache.shiro.mgt.SecurityManager])
    SecurityUtils.setSecurityManager(securityManager)
    i
  }

  override def contextInitialized(servletContextEvent: ServletContextEvent) {
    sc = servletContextEvent.getServletContext
    super.contextInitialized(servletContextEvent)
  }
}

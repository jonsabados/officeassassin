package com.jshnd.assassin.rest

import scala.collection.JavaConversions._
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.{Singleton => GSingleton, TypeLiteral, Guice, Injector}
import java.util.Properties
import com.sun.jersey.guice.JerseyServletModule
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer
import com.jshnd.assassin.AssassinRootModule
import com.jshnd.assassin.jpa.JpaStoreModuleInitializer
import org.apache.shiro.guice.web.{GuiceShiroFilter, ShiroWebModule}
import javax.servlet._
import com.jshnd.shiro._
import org.apache.shiro.authc.credential.{HashedCredentialsMatcher, CredentialsMatcher}
import org.apache.shiro.crypto.hash.Sha256Hash
import com.jshnd.assassin.rest.bindings.PasswordHasher
import com.google.inject.matcher.Matchers._
import com.jshnd.assassin.rest.exceptionmapping.UnauthorizedExceptionMapper
import org.apache.bval.guice.ValidationModule
import com.jshnd.validation.{ValidationInterceptor, DoValidation}

class AssassinServletConfig extends GuiceServletContextListener {

  var sc: ServletContext = _

  class SecurityModule extends ShiroWebModule(sc) {
    def configureShiroWeb() {
      bindRealm().to(classOf[InjectionRealm])
      bind(classOf[AuthorizationInfoSource]).to(classOf[AssassinAuthorizationSource])
      bind(classOf[AuthenticationInfoSource]).to(classOf[AssassinAuthenticationSource])
      bind(classOf[CredentialsMatcher]).toInstance(new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME))

      val hasherType = new TypeLiteral[(String, String) => String] {}
      bind(hasherType).annotatedWith(classOf[PasswordHasher]).toInstance(hashPassword)
      expose(hasherType).annotatedWith(classOf[PasswordHasher])

      addFilterChain("/rest/public/**", ShiroWebModule.ANON)
      //addFilterChain("/rest/**", ShiroWebModule.NO_SESSION_CREATION) // TODO - this appears to do nothing and breaks anon... see SessionDieFilter and figure out if were just doing something horribly wrong
      addFilterChain("/rest/**", ShiroWebModule.AUTHC_BASIC)
    }

    def hashPassword(email: String, password: String): String = new Sha256Hash(password, email).toHex

  }

  class AssassinServletModule extends JerseyServletModule {
    override def configureServlets() {
      install(new AssassinRootModule(propertyFile.toMap))
      install(new ValidationModule)

      // the bval interceptor doesn't appear to look for @Valid annotations... so well just roll our own.
      val validator = new ValidationInterceptor
      requestInjection(validator)
      bindInterceptor(any(), annotatedWith(classOf[DoValidation]), validator)

      bindInterceptor(any(), annotatedWith(classOf[RequiresPermission]), new RequiresPermissionInterceptor())

      bind(classOf[UnauthorizedExceptionMapper]).in(classOf[GSingleton])

      bind(classOf[UserResource]).in(classOf[GSingleton])
      bind(classOf[EnlistmentResource]).in(classOf[GSingleton])

      serve("/rest/*").`with`(classOf[GuiceContainer])
      filter("/rest/*").through(classOf[GuiceShiroFilter])
    }

  }

  def propertyFile: Properties = {
    val props = new Properties()
    props.load(getClass.getResourceAsStream("/default-assassin-config.properties"))
    props
  }

  def getInjector: Injector = {
    val i = Guice.createInjector(new SecurityModule, new AssassinServletModule)
    i.getInstance(classOf[JpaStoreModuleInitializer]).start()
    i
  }

  override def contextInitialized(servletContextEvent: ServletContextEvent) {
    sc = servletContextEvent.getServletContext
    super.contextInitialized(servletContextEvent)
  }

}

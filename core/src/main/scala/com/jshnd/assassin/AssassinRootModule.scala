package com.jshnd.assassin

import scala.collection.JavaConversions._
import com.google.inject.{Guice, Module, AbstractModule}
import com.google.inject.name.Names
import com.jshnd.assassin.user.UserModule
import com.jshnd.assassin.permissions.DefaultUserPermissionModule

class AssassinRootModule(configuration: Map[String, String]) extends AbstractModule {

  class BootstrapModule extends AbstractModule {
    def configure() {
      Names.bindProperties(binder(), configuration)
    }
  }

  def configure() {
    val bootstrapInjector = Guice.createInjector(new BootstrapModule())
    Names.bindProperties(binder(), configuration)
    install(bootstrapInjector.getInstance(
      Class.forName(configuration(CONFIG_KEY_SESSION_MODULE))).asInstanceOf[Module])

    val userMod = new UserModule
    requestInjection(userMod)
    install(userMod)
    install(new DefaultUserPermissionModule)
  }
}

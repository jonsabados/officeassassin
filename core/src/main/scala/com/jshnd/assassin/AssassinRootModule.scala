package com.jshnd.assassin

import scala.collection.JavaConversions._
import com.google.inject.{Guice, TypeLiteral, Module, AbstractModule}
import com.google.inject.name.Names

object AssassinRootModule {
  val storeModuleClassKey = "module.store.class"
}

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
      Class.forName(configuration(AssassinRootModule.storeModuleClassKey))).asInstanceOf[Module])

  }
}

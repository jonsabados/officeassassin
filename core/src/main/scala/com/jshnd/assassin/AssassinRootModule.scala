package com.jshnd.assassin

import com.google.inject.{TypeLiteral, Module, AbstractModule}
import com.jshnd.assassin.bindings.AssassinConfiguration

object AssassinRootModule {
  val storeModuleClassKey = "module.store.class"
}

class AssassinRootModule(configuration: Map[String, String]) extends AbstractModule {
  def configure() {
    bind(new TypeLiteral[Map[String, String]] {}).annotatedWith(classOf[AssassinConfiguration]).toInstance(configuration)
    install(Class.forName(configuration(AssassinRootModule.storeModuleClassKey)).newInstance().asInstanceOf[Module])
  }
}

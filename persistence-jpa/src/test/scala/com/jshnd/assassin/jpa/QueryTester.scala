package com.jshnd.assassin.jpa

import com.google.inject.Guice
import com.google.inject.persist.PersistService
import com.jshnd.assassin.AssassinRootModule

trait QueryTester {

  val config = Map(AssassinRootModule.storeModuleClassKey -> "com.jshnd.assassin.jpa.JpaTestModule")

  val injector = Guice.createInjector(new AssassinRootModule(config))

  val initializer = injector.getInstance(classOf[JpaStoreModuleInitializer])
  initializer.start()
  val testStore = injector.getInstance(classOf[JpaAssassinStore])

}

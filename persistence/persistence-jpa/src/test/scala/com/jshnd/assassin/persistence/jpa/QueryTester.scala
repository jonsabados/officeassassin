package com.jshnd.assassin.persistence.jpa

import com.google.inject.Guice
import com.google.inject.persist.PersistService

trait QueryTester {

  val injector = Guice.createInjector(new JpaTestModule())
  injector.getInstance(classOf[PersistService]).start()

  val testStore = injector.getInstance(classOf[JpaAssassinStore])

}

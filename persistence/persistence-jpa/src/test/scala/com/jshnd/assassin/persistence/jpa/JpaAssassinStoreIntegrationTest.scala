package com.jshnd.assassin.persistence.jpa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import com.google.inject.Guice
import com.google.inject.persist.PersistService

@RunWith(classOf[JUnitRunner])
class JpaAssassinStoreIntegrationTest extends FunSpec {

   it("Should do shit and stuff") {
     val i = Guice.createInjector(new JpaTestModule())
     i.getInstance(classOf[PersistService]).start()
     val testStore = i.getInstance(classOf[JpaAssassinStore])
     println(testStore)
   }

}

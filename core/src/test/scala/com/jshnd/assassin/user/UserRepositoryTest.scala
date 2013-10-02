package com.jshnd.assassin.user

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.{Inside, FunSpec}
import com.google.inject.{Guice}
import com.jshnd.assassin._
import com.jshnd.assassin.persistence.{SchemaUpdater, SessionModule}

@RunWith(classOf[JUnitRunner])
class UserRepositoryTest extends FunSpec with Inside {

  describe("UserRepository") {
    val i = Guice.createInjector(new AssassinRootModule(Map(
        CONFIG_KEY_SESSION_MODULE -> "com.jshnd.assassin.persistence.EmbeddedDerbyModule",
        CONFIG_KEY_EMBEDDED_DB_LOCATION -> "memory:test"
    )))

    i.getInstance(classOf[SchemaUpdater]).updateSchema()

    val testObj = i.getInstance(classOf[UserRepository])

    // TODO - dbunit or something for setup, this works for now...
    it("Should find the admin user") {
      inside(testObj.findByEmail("admin")) {
        case Some(User("admin", _, _, _)) =>
      }
    }
  }

}

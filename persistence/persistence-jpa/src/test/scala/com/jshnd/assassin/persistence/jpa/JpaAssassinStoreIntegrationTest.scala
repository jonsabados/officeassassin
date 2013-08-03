package com.jshnd.assassin.persistence.jpa

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import com.google.inject.Guice
import com.google.inject.persist.PersistService
import com.jshnd.assassin.persistence.user.{UserQuery, User}

@RunWith(classOf[JUnitRunner])
class JpaAssassinStoreIntegrationTest extends FunSpec {

   it("Should do shit and stuff") {
     val i = Guice.createInjector(new JpaTestModule())
     i.getInstance(classOf[PersistService]).start()
     val testStore = i.getInstance(classOf[JpaAssassinStore])
     testStore.persist(new User(None, "foo@foo.com", "Foo Foe", Some("Buis"), "123"))
     testStore.persist(new User(None, "bar@foo.com", "Bar Bing", None, "123"))

     val query = new UserQuery(Some("foo@foo.com"))

     assert(testStore.find(query) === List(User(None, "foo@foo.com", "Foo Foe", Some("Buis"), "123")))
   }

}

package com.jshnd.assassin

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.jshnd.assassin.user.User
import com.jshnd.assassin.AssassinSchema._
import com.jshnd.assassin.bindings.Transactional
import com.google.inject.{Guice, Inject}
import org.scalatest.FunSpec
import com.jshnd.assassin.persistence.SchemaUpdater
import java.sql.Connection

class PartialTxnTestBit {

  @Transactional
  def save(user: User) {
    users.insert(user)
  }

}

class FullTxnTestBit @Inject() (p: PartialTxnTestBit) {

  @Transactional
  def doBadInsertion(email: String) {
    p.save(new User(email, "foo", None, "123"))
    p.save(new User(email, "foo", None, "123"))
  }

}

@RunWith(classOf[JUnitRunner])
class PersistenceSanityTest extends FunSpec {

  describe("Assassin @Transactional behavior") {
    val i = Guice.createInjector(new AssassinRootModule(Map(
      CONFIG_KEY_SESSION_MODULE -> "com.jshnd.assassin.persistence.EmbeddedDerbyModule",
      CONFIG_KEY_EMBEDDED_DB_LOCATION -> "memory:test"
    )))

    i.getInstance(classOf[SchemaUpdater]).updateSchema()

    it("Should commit when nothing goes wrong") {
      val email = "single"
      i.getInstance(classOf[PartialTxnTestBit]).save(new User(email, "ok", None, "123"))
      assert(1 === userCount(email))
    }

    it("Should be all or nothing inside a transaction") {
      val dupeEmail = "duplicatesAbound"
      val testBit = i.getInstance(classOf[FullTxnTestBit])
      // ugh... squeryl just chucks generic runtime exceptions
      intercept[RuntimeException] {
        testBit.doBadInsertion(dupeEmail)
      }
      assert(0 === userCount(dupeEmail))
    }

    def userCount(username: String): Long = {
      val conn = i.getInstance(classOf[Connection])
      try {
        val stmt = conn.prepareStatement("select count(*) from users where email_address=?")
        stmt.setString(1, username)
        val res = stmt.executeQuery()
        res.next()
        res.getLong(1)
      } finally {
        conn.close()
      }
    }

  }

}

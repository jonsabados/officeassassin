package com.jshnd.assassin.persistence

import org.squeryl.internals.DatabaseAdapter
import java.sql.Connection
import org.squeryl.adapters.DerbyAdapter
import com.google.inject.name.Named
import org.apache.derby.jdbc.EmbeddedDataSource
import javax.inject.Inject

class EmbeddedDerbyModule @Inject() (@Named("embeddedDb.location") dbLocation: String) extends SessionModule {

  val ds = new EmbeddedDataSource
  ds.setCreateDatabase("create")
  ds.setDatabaseName(dbLocation)

  def connection: Connection = ds.getConnection

  def adapter: DatabaseAdapter = new DerbyAdapter {
    override def quoteIdentifier(s: String): String = s
  }

}

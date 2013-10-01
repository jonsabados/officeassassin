package com.jshnd.assassin.persistence

import com.jshnd.assassin._
import org.squeryl.internals.DatabaseAdapter
import java.sql.Connection
import org.squeryl.adapters.DerbyAdapter
import org.slf4j.LoggerFactory
import com.google.inject.name.Named
import org.apache.derby.jdbc.EmbeddedDataSource

class EmbeddedDerbyModule(@Named(CONFIG_KEY_EMBEDDED_DB_LOCATION) dbLocation: String) extends SessionModule with SchemaUpdater {

  val logger = LoggerFactory.getLogger(classOf[EmbeddedDerbyModule])

  def connection: Connection = {
    val ret = new EmbeddedDataSource
    ret.setCreateDatabase("create")
    ret.setDatabaseName(dbLocation)
    ret.getConnection
  }

  def adapter: DatabaseAdapter = new DerbyAdapter

  def start() {
    logger.info("Using DB in " + dbLocation)
    if(needsUpdate()) {
      logger.info("Updating schema")
      updateSchema()
    } else {
      logger.info("Schema up to date")
    }
  }
}

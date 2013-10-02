package com.jshnd.assassin.persistence

import java.sql.Connection
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import com.google.inject.Inject
import org.slf4j.LoggerFactory

class SchemaUpdater @Inject() (connection: Connection) {

  val logger = LoggerFactory.getLogger(classOf[EmbeddedDerbyModule])

  def updateIfNeeded() {
    if(needsUpdate()) {
      logger.info("Updating schema")
      updateSchema()
    } else {
      logger.info("Schema up to date")
    }
  }

  def needsUpdate(): Boolean = {
    inLiquibase[Boolean](l => l.listUnrunChangeSets(null).size() > 0)
  }

  def updateSchema() {
    inLiquibase(l => l.update(null))
  }

  private def inLiquibase[T](callback: (Liquibase) => T): T = {
    val conn = connection
    try {
      val  database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn))
      val  l = new Liquibase("schema.xml", new ClassLoaderResourceAccessor(), database)
      callback(l)
    } finally conn.close()
  }

}

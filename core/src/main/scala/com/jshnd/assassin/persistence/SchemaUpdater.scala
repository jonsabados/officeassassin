package com.jshnd.assassin.persistence

import java.sql.Connection
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor

trait SchemaUpdater {

  def connection: Connection

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

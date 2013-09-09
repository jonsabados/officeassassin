package com.jshnd.assassin.rest

import com.jshnd.assassin.jpa.JpaStoreModule
import javax.sql.DataSource
import com.google.inject.Inject
import com.google.inject.name.Named
import org.apache.derby.jdbc.EmbeddedDataSource

class EmbeddedJpaStoreModule @Inject() (@Named("embeddedDb.location") dbLocation: String) extends JpaStoreModule("org.hibernate.dialect.DerbyDialect") {
  def dataSource: DataSource = {
    val ret = new EmbeddedDataSource
    ret.setCreateDatabase("create")
    // TODO - real logging
    System.out.println("Using " + dbLocation)
    ret.setDatabaseName(dbLocation)
    ret
  }

  def jpaProperties: Map[String, String] = Map(
    "javax.persistence.jdbc.driver" -> "org.apache.derby.jdbc.EmbeddedDriver",
    "javax.persistence.jdbc.url" -> ("jdbc:derby:" + dbLocation)
  )
}

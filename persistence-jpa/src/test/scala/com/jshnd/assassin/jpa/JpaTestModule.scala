package com.jshnd.assassin.jpa

import javax.sql.DataSource
import org.apache.derby.jdbc.EmbeddedDataSource

class JpaTestModule extends JpaStoreModule("org.hibernate.dialect.DerbyDialect") {

  def dataSource: DataSource = {
    val ds = new EmbeddedDataSource()
    ds.setCreateDatabase("create")
    ds.setDatabaseName("memory:test")
    ds
  }

  def jpaProperties: Map[String, String] = Map(
    "javax.persistence.jdbc.driver" ->  "org.apache.derby.jdbc.EmbeddedDriver",
    "javax.persistence.jdbc.url" -> "jdbc:derby:memory:test;create=true"
  )

}

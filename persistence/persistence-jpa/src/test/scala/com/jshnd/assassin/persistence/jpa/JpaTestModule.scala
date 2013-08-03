package com.jshnd.assassin.persistence.jpa

import com.google.inject.{AbstractModule}
import com.google.inject.persist.jpa.JpaPersistModule
import java.util.Properties
import liquibase.Liquibase
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.database.jvm.{JdbcConnection}
import liquibase.database.DatabaseFactory
import java.sql.DriverManager

class JpaTestModule extends AbstractModule {
  def configure() {
    Class.forName("org.h2.Driver").newInstance();
    val conn = DriverManager.getConnection("jdbc:h2:mem:test");
    val  database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn));
    val  liquibase = new Liquibase("schema.xml", new ClassLoaderResourceAccessor(), database);
    liquibase.update(null);

    val persistModule = new JpaPersistModule("assassin")
    val props = new Properties()
    props.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
    props.put("javax.persistence.jdbc.driver", "org.h2.Driver")
    props.put("javax.persistence.jdbc.url","jdbc:h2:mem:test")
    persistModule.properties(props)
    install(persistModule)
  }
}

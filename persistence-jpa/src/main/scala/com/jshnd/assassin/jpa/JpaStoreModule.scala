package com.jshnd.assassin.jpa

import scala.collection.JavaConversions._
import com.google.inject.{Inject, AbstractModule}
import com.google.inject.persist.jpa.JpaPersistModule
import java.util.Properties
import javax.sql.DataSource
import com.jshnd.assassin.persistence.jpa.AssassinDataSource
import com.google.inject.persist.PersistService
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.Liquibase
import liquibase.resource.ClassLoaderResourceAccessor

object JpaStoreModule {
  val dialectKey = "jpa.hibernate.dialect"
  val poolSizeKey = "jpa.pool.size"
}

class JpaStoreModuleInitializer @Inject() (@AssassinDataSource dataSource: DataSource,
                                           persistService: PersistService)  {

  def start() {
    // TODO - real logging
    println("Updating schema...")
    val conn = dataSource.getConnection
    try {
      val  database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn))
      val  liquibase = new Liquibase("schema.xml", new ClassLoaderResourceAccessor(), database)
      liquibase.update(null)
    } finally conn.close()

    println("Starting persist service")
    persistService.start()
  }

}

abstract class JpaStoreModule(dialect: String) extends AbstractModule {

  def configure() {
    val persistModule = new JpaPersistModule("assassin")
    val props = new Properties()
    props.put("hibernate.dialect", dialect)
    props.putAll(jpaProperties)
    persistModule.properties(props)

    install(persistModule)
    bind(classOf[DataSource]).annotatedWith(classOf[AssassinDataSource]).toInstance(dataSource)
    bind(classOf[JpaAssassinStore]).in(classOf[com.google.inject.Singleton])
  }

  def dataSource: DataSource

  /**
   * This must return all properties necessary to configure jpa for use with
   * the DataSource returned by dataSource, minus hibernate.dialect. (sigh... this
   * would not be needed if you could just inject a data source into an entity manager)
   *
   * @return map of properties used to configure jpa.
   */
  def jpaProperties: Map[String, String]
}

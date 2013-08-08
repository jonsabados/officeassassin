package com.jshnd.assassin.rest

import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.{Singleton => GSingleton, Guice, Injector}
import java.sql.DriverManager
import com.google.inject.persist.jpa.JpaPersistModule
import java.util.Properties
import com.sun.jersey.guice.JerseyServletModule
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.Liquibase
import com.google.inject.persist.PersistService
import com.jshnd.assassin.query.AssassinStore
import com.jshnd.assassin.jpa.JpaAssassinStore

class AssassinServletConfig extends GuiceServletContextListener {

  class LetsSeeIfThisWorks extends JerseyServletModule {
    override def configureServlets() {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance()
      val conn = DriverManager.getConnection("jdbc:derby:memory:test;create=true")
      val  database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn))
      val  liquibase = new Liquibase("schema.xml", new ClassLoaderResourceAccessor(), database)
      liquibase.update(null)

      val persistModule = new JpaPersistModule("assassin")
      val props = new Properties()
      props.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect")
      props.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver")
      props.put("javax.persistence.jdbc.url","jdbc:derby:memory:test;create=true")
      persistModule.properties(props)
      install(persistModule)

      bind(classOf[AssassinStore]).to(classOf[JpaAssassinStore]).in(classOf[GSingleton])
      bind(classOf[UserResource]).in(classOf[GSingleton])

      serve("/rest/*").`with`(classOf[GuiceContainer])
    }
  }

  def getInjector: Injector = {
    val i = Guice.createInjector(new LetsSeeIfThisWorks)
    i.getInstance(classOf[PersistService]).start()
    i
  }
}

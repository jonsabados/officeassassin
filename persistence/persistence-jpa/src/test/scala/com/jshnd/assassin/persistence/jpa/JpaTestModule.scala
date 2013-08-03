package com.jshnd.assassin.persistence.jpa

import com.google.inject.{Guice, AbstractModule}
import com.google.inject.persist.jpa.JpaPersistModule
import java.util.Properties
import com.google.inject.persist.PersistService

class JpaTestModule extends AbstractModule {
  def configure() {
    val persistModule = new JpaPersistModule("assassin")
    val props = new Properties()
    props.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect")
    props.put("javax.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver")
    props.put("javax.persistence.jdbc.url","jdbc:derby:test")
    props.put("javax.persistence.jdbc.user","root")
    props.put("javax.persistence.jdbc.password","root")
    persistModule.properties(props)
    install(persistModule)
//    bind(classOf[JpaTypeMapperFactory]).to(classOf[JpaTypeMapperFactory])
//    bind(classOf[JpaAssassinStore]).to(classOf[JpaAssassinStore])
  }
}

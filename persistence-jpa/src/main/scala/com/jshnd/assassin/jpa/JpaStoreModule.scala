package com.jshnd.assassin.jpa

import com.google.inject.{Inject, AbstractModule}
import com.google.inject.persist.jpa.JpaPersistModule
import com.google.inject.name.Named
import java.util.Properties

object JpaStoreModule {

}

class JpaStoreModule @Inject() (@Named("hibernate.dialect") dialect: String) extends AbstractModule {
  def configure() {
    val persistModule = new JpaPersistModule("assassin")
    val props = new Properties()
    props.put("hibernate.dialect", dialect)

    persistModule.properties(props)

    install(persistModule)
    bind(classOf[JpaAssassinStore]).in(classOf[com.google.inject.Singleton])
  }
}

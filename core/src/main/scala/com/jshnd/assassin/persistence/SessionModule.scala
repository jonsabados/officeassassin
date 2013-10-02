package com.jshnd.assassin.persistence

import com.google.inject.{Provides, AbstractModule}
import java.sql.Connection
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.{SessionFactory, Session}
import com.google.inject.matcher.Matchers
import com.jshnd.assassin.bindings.Transactional

abstract class SessionModule extends AbstractModule {

  def adapter: DatabaseAdapter

  def configure() {
    SessionFactory.concreteFactory = Some(() => session)
    val transactionInterceptor = new SquerylTransactionInterceptor
    requestInjection(transactionInterceptor)
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[Transactional]), transactionInterceptor)
    bind(classOf[SchemaUpdater])
  }

  @Provides
  def connection: Connection

  def session: Session = {
    Session.create(connection, adapter)
  }

}

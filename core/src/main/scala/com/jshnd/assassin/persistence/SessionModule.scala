package com.jshnd.assassin.persistence

import com.google.inject.{Provides, AbstractModule}
import java.sql.Connection
import org.squeryl.internals.DatabaseAdapter
import org.squeryl.Session
import com.google.inject.matcher.Matchers
import com.jshnd.assassin.bindings.Transactional

trait SessionModule extends AbstractModule {

  def connection: Connection

  def adapter: DatabaseAdapter

  def configure() {
    val transactionInterceptor = new SquerylTransactionInterceptor
    requestInjection(transactionInterceptor)
    bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[Transactional]), transactionInterceptor)
  }

  @Provides
  def session: Session = {
    Session.create(connection, adapter)
  }

  def start()
}

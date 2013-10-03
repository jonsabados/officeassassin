package com.jshnd.assassin

import org.scalatest.{Suite, BeforeAndAfterAll}
import org.dbunit.AbstractDatabaseTester
import org.dbunit.dataset.IDataSet
import org.dbunit.database.{DatabaseConnection, IDatabaseConnection}
import java.sql.Connection
import com.google.inject.Guice
import com.jshnd.assassin.persistence.SchemaUpdater
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder

trait DbTester extends BeforeAndAfterAll with Suite {

  val injector = Guice.createInjector(new AssassinRootModule(Map(
    CONFIG_KEY_SESSION_MODULE -> "com.jshnd.assassin.persistence.EmbeddedDerbyModule",
    CONFIG_KEY_EMBEDDED_DB_LOCATION -> "memory:test"
  )))

  injector.getInstance(classOf[SchemaUpdater]).updateSchema()

  val tester = new AbstractDatabaseTester() {
    def getConnection: IDatabaseConnection = new DatabaseConnection(connection())
  }

  override def beforeAll() {
    tester.setDataSet(dataSet())
    tester.onSetup()
  }

  override def afterAll() {
    tester.onTearDown()
  }

  def testData: String

  private def dataSet(): IDataSet = new FlatXmlDataSetBuilder().build(getClass.getResourceAsStream(testData))

  protected def connection(): Connection = injector.getInstance(classOf[Connection])

}

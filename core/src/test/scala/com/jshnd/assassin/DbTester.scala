package com.jshnd.assassin

import org.scalatest._
import org.dbunit.AbstractDatabaseTester
import org.dbunit.dataset.IDataSet
import org.dbunit.database.{DatabaseConnection, IDatabaseConnection}
import java.sql.Connection
import com.google.inject.Guice
import com.jshnd.assassin.persistence.SchemaUpdater
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import scala.Some

trait DbTester extends AbstractSuite {

  this: Suite =>

  val injector = Guice.createInjector(new AssassinRootModule(Map(
    CONFIG_KEY_SESSION_MODULE -> "com.jshnd.assassin.persistence.EmbeddedDerbyModule",
    CONFIG_KEY_EMBEDDED_DB_LOCATION -> "memory:test"
  )))

  val tester = new AbstractDatabaseTester() {
    def getConnection: IDatabaseConnection = new DatabaseConnection(connection())
  }

  def testData: String

  private def dataSet(): IDataSet = new FlatXmlDataSetBuilder().build(getClass.getResourceAsStream(testData))

  protected def connection(): Connection = injector.getInstance(classOf[Connection])

  def setup() {
    injector.getInstance(classOf[SchemaUpdater]).updateSchema()
    tester.setDataSet(dataSet())
    tester.onSetup()
  }

  def teardown() {
    tester.onTearDown()
  }

  // shamelessly stolen from BeforeAndAfterAll
  abstract override def run(testName: Option[String], reporter: Reporter, stopper: Stopper, filter: Filter,
                            configMap: Map[String, Any], distributor: Option[Distributor], tracker: Tracker) {
    var thrownException: Option[Throwable] = None

    setup()
    try {
      super.run(testName, reporter, stopper, filter, configMap, distributor, tracker)
    }
    catch {
      case e: Exception => thrownException = Some(e)
    }
    finally {
      try {
        teardown() // Make sure that afterAll is called even if run completes abruptly.
        thrownException match {
          case Some(e) => throw e
          case None =>
        }
      }
      catch {
        case laterException: Exception =>
          thrownException match { // If both run and afterAll throw an exception, report the test exception
            case Some(earlierException) => throw earlierException
            case None => throw laterException
          }
      }
    }
  }

}

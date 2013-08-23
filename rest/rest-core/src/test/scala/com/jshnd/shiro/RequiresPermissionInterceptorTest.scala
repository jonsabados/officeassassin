package com.jshnd.shiro

import com.google.inject.{Guice, AbstractModule}
import org.apache.shiro.subject.Subject
import com.google.inject.matcher.Matchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.mockito.Mockito._
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.util.ThreadContext
import javax.ws.rs.PathParam

@RunWith(classOf[JUnitRunner])
class RequiresPermissionInterceptorTest extends FunSpec {

  class TestModule extends AbstractModule {
    def configure() {
      bindInterceptor(Matchers.any(), Matchers.annotatedWith(classOf[RequiresPermission]), new RequiresPermissionInterceptor())
      bind(classOf[Tester])
    }
  }

  val injector = Guice.createInjector(new TestModule())
  val tester = injector.getInstance(classOf[Tester])

  describe("RequiresPermissionInterceptor") {
    it("Should throw AuthorizationExceptions when permissions are not present") {
      val subj = mock(classOf[Subject])
      ThreadContext.put(ThreadContext.SUBJECT_KEY, subj)
      when(subj.checkPermission("foo:fizz:buzz")).thenThrow(classOf[AuthorizationException])
      intercept[AuthorizationException] {
        tester.doSomething("fizz", "hi", "buzz")
      }
    }

    it("Should allow methods to proceed when permissions are present") {
      val subj = mock(classOf[Subject])
      ThreadContext.put(ThreadContext.SUBJECT_KEY, subj)
      tester.doSomething("saw", "hi", "it")
      verify(subj).checkPermission("foo:saw:it")
    }
  }

}

class Tester {

  @RequiresPermission("foo:{subA}:{b}")
  def doSomething(@PathParam("noiseA") @Substitution("subA") subA: String,
                  toWhat: Object,
                  @Substitution("b") @PathParam("noiseB") subB: String) {}

}
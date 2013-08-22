package com.jshnd.assassin.rest

import org.scalatest.FunSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import javax.ws.rs.Path

@RunWith(classOf[JUnitRunner])
class packageTest extends FunSpec {

  @Path("foo")
  class DummyResource {}

  describe("Rest package object") {

    describe("resourcePath") {
      it("Should return the proper path for a resource") {
        assert(resourcePath(classOf[DummyResource]) === "foo")
      }
    }
  }

}

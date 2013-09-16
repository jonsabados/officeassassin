package com.jshnd.assassin

import org.scalatest.{Inside, GivenWhenThen, FeatureSpec}
import dispatch._, Defaults._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UserResourceFeature extends FeatureSpec with StartsAssassin with GivenWhenThen with Inside {

  feature("Request a specific user - json") {

    info("As an api consumer")
    info("Using json")
    info("I should be able to look up my information")

    scenario("The user exists, and has a full name") {
      Given("I know what my email address is")
      val email = "userResourceFeature1@test.com"
      And("I know what my password is")
      val password = "12345"
      And("I have previously enrolled")
      enlist(email, "userResourceFeature1", Some("Joe bob"), password)

      When("I prepare a GET request to rest/users/email/{my email address}")
      var svc = url(s"$baseUrl/rest/users/email/$email").GET
      And("I set the header Accept to application/json")
      svc = svc.setHeader("Accept", "application/json")
      And("I use basic authentication with my email address and my password")
      svc = svc.setHeader("Authorization", authHeader(email, password))
      val fetch = Http(svc OK asJsonObject)

      Then("I should receive a response with valid json content")
      val objectMap = fetch()
      And("The id field should be an integer")
      objectMap("id") match {
        // TODO - looks like the scala json stuff parses numbers so they come back as either double or floats, json content actually does represent it properly (no decimals)
        case id: Number => assert(id === id.intValue())
        case x: Any => fail("Id was not an int - got " + x)
      }
      And("The emailAddress field should match my email address")
      assert(email === objectMap("emailAddress"))
      And("The handle field should match my handle")
      assert("userResourceFeature1" === objectMap("handle"))
      And("The fullName field should match my full name")
      assert("Joe bob" === objectMap("fullName"))
    }

    // TODO - fix the duplication... it works for now
    scenario("The user exists, and does not have a full name") {
      Given("I know what my email address is")
      val email = "userResourceFeature2@test.com"
      And("I know what my password is")
      val password = "12345"
      And("I have previously enrolled")
      enlist(email, "userResourceFeature2", None, password)

      When("I prepare a GET request to rest/users/email/{my email address}")
      var svc = url(s"$baseUrl/rest/users/email/$email").GET
      And("I set the header Accept to application/json")
      svc = svc.setHeader("Accept", "application/json")
      And("I use basic authentication with my email address and my password")
      svc = svc.setHeader("Authorization", authHeader(email, password))
      val fetch = Http(svc OK asJsonObject)

      Then("I should receive a response with valid json content")
      val objectMap = fetch()
      And("The id field should be an integer")
      objectMap("id") match {
        // TODO - looks like the scala json stuff parses numbers so they come back as either double or floats, json content actually does represent it properly (no decimals)
        case id: Number => assert(id === id.intValue())
        case x: Any => fail("Id was not an int - got " + x)
      }
      And("The emailAddress field should match my email address")
      assert(email === objectMap("emailAddress"))
      And("The handle field should match my handle")
      assert("userResourceFeature2" === objectMap("handle"))
      And("The fullName should be empty")
      assert(null == objectMap("fullName"))
    }

    scenario("The user does not exist") {

      pending
    }

    scenario("I am not authenticated") {

      pending
    }

    scenario("I use the wrong password") {

      pending
    }

  }

}

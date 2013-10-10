package com.jshnd.assassin.integration

import org.scalatest.{Inside, GivenWhenThen, FeatureSpec}
import dispatch._, Defaults._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import javax.servlet.http.HttpServletResponse

@RunWith(classOf[JUnitRunner])
class UserResourceFeature extends FeatureSpec with IntegrationTest with GivenWhenThen with Inside {

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

  feature("Request a users roles") {

    info("As an api consumer")
    info("Using json")
    info("I should be able to look up roles")

    scenario("I look up my own roles as a normal user") {
      Given("I have enlisted and I know my id")
      val email = "userResourceFeature3@test.com"
      val password = "12345"
      enlist(email, "userResourceFeature3", None, password)
      val id = userId(email, email, password)

      When("I lookup my roles")
      val svc = jsonResponse(withAuth(url(s"$baseUrl/rest/users/id/$id/roles").GET, email, password))
      val get = Http(svc OK asJsonObject)

      Then("The result should contain the role 'user'")
      val result = get()
      val items = result("data").asInstanceOf[List[Map[String, _]]]
      assert(items.indexWhere(r => r("name") == "user") != -1)
    }

    scenario("I look up someone else's roles as a normal user") {
      Given("I have enlisted")
      val email = "userResourceFeature4@test.com"
      val password = "12345"
      enlist(email, "userResourceFeature4", None, password)

      And("I know another users id")
      val target = "userResourceFeature5@test.com"
      enlist(target, "userResourceFeature5", None, "foo")
      val id = userId(target, email, password)

      When("I lookup their roles")
      val svc = jsonResponse(withAuth(url(s"$baseUrl/rest/users/id/$id/roles").GET, email, password))
      val get = Http(svc > asResponseCode)

      Then("I should be forbidden")
      val result = get()
      assert(result === HttpServletResponse.SC_FORBIDDEN)
    }

    scenario("I look up someone else's roles as a super user") {
      Given("I am an admin and I know a users id")
      val adminUser = "admin"
      val password = "changeme"
      val email = "userResourceFeature6@test.com"
      enlist(email, "userResourceFeature6", None, "bar")
      val id = userId(email, adminUser, password)

      When("I lookup their roles")
      val svc = jsonResponse(withAuth(url(s"$baseUrl/rest/users/id/$id/roles").GET, adminUser, password))
      val get = Http(svc OK asJsonObject)

      Then("The result should contain the role 'user'")
      val result = get()
      val items = result("data").asInstanceOf[List[Map[String, _]]]
      assert(items.indexWhere(r => r("name") == "user") != -1)
    }

  }

}

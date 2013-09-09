package com.jshnd.assassin

import org.scalatest.{GivenWhenThen, FeatureSpec}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import dispatch._, Defaults._

@RunWith(classOf[JUnitRunner])
class EnlistmentFeature extends FeatureSpec with StartsAssassin with GivenWhenThen {

  def asNewResource = as.Response[String] { response =>
    response.getHeader("Location")
  }

  feature("Enlistment Resource - json content") {

    info("As an api consumer")
    info("Using json")
    info("I should be able to enlist users")

    scenario("A user enlists without errors") {
      Given("A valid create request")
      val json = "{\"fullName\":\"John Doe\",\"password\":\"12345\",\"emailAddress\":\"jd@example.com\",\"handle\": \"jd\"}"
      val svc = url("http://localhost:8080/rest/public/enlistment")
      val post = svc.addHeader("Content-Type", "application/json") << json
      val enroll = Http(post OK asNewResource)

      When("The request is sent")
      val location = enroll()

      Then("I should be able to query the user using the users credentials")
      val check = url(location).GET.addHeader("Authorization", "Basic amRAZXhhbXBsZS5jb206MTIzNDU=")
      val followup = Http(check OK as.String)
      assert(followup().contains("jd@example.com"))
    }

    scenario("The email address used is already in use") {
      pending
    }

    scenario("The handle used is already in use") {
      pending
    }
  }

}

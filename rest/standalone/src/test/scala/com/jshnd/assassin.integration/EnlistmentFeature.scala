package com.jshnd.assassin.integration

import org.scalatest.{GivenWhenThen, FeatureSpec}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import dispatch._, Defaults._

@RunWith(classOf[JUnitRunner])
class EnlistmentFeature extends FeatureSpec with IntegrationTest with GivenWhenThen {

  feature("Enlistment - json content") {

    info("As an api consumer")
    info("Using json")
    info("I should be able to enlist users")

    scenario("A user enlists without errors") {
      Given(s"The enlistment resource lives at $baseUrl/rest/public/enlistment")
      val json = "{\"fullName\":\"John Doe\",\"password\":\"12345\",\"emailAddress\":\"jd@example.com\",\"handle\": \"jd\"}"
      val svc = url(s"$baseUrl/rest/public/enlistment")
      val post = svc.addHeader("Content-Type", "application/json") << json
      val enroll = Http(post OK asNewResource)

      When("The request is sent")
      val location = enroll()

      Then("I should be able to query the user using the users credentials")
      val check = url(location).GET.addHeader("Authorization", authHeader("jd@example.com", "12345"))
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

  feature("Enlistment - xml content") {

    info("As an api consumer")
    info("Using xml")
    info("I should be able to enlist users")

    scenario("A user enlists without errors") {
      Given("A valid create request")
      val xml =
        <user>
          <emailAddress>xmluser@example.com</emailAddress>
          <handle>xml</handle>
          <fullName>XML Rulz!</fullName>
          <password>12345</password>
        </user>

      val svc = url(s"$baseUrl/rest/public/enlistment")
      val post = svc.addHeader("Content-Type", "application/xml") << xml.toString
      val enroll = Http(post OK asNewResource)

      When("The request is sent")
      val location = enroll()

      Then("I should be able to query the user using the users credentials")
      val check = url(location).GET.addHeader("Authorization", authHeader("xmluser@example.com", "12345"))
      val followup = Http(check OK as.String)
      assert(followup().contains("xmluser@example.com"))
    }

    scenario("The email address used is already in use") {
      pending
    }

    scenario("The handle used is already in use") {
      pending
    }
  }

}

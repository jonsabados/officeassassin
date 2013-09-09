package com.jshnd.assassin

import org.scalatest.FeatureSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EnlistmentFeature extends FeatureSpec with StartsAssassin {

  feature("Enlistment Resource - json content") {

    info("As an api consumer")
    info("I should be able to enlist users")

    scenario("A user enlists without errors") {
      pending
    }

    scenario("The email address used is already in use") {
      pending
    }

    scenario("The handle used is already in use") {
      pending
    }
  }

}

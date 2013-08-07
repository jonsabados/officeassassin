package com.jshnd.assassin

import com.google.inject.Inject
import com.jshnd.assassin.bindings.AssassinConfiguration

trait HasAssassinConfiguration {

  @Inject
  @AssassinConfiguration
  var configuration: Map[String, String] = _

}

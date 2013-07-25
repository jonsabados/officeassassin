package com.jshnd.assassin.persistence

import scala.reflect.runtime.universe._

trait AnnotatedQuery {

  def predicate(): AssassinQueryPredicate = {

    NoPredicate()
  }

}

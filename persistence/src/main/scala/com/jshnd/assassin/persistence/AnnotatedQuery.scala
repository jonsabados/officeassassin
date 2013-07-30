package com.jshnd.assassin.persistence

trait AnnotatedQuery {

  def predicate(): AssassinQueryPredicate = {

    NoPredicate()
  }

}

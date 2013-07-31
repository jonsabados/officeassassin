package com.jshnd.assassin.persistence

abstract class AssassinQueryPredicate
case class NoPredicate() extends AssassinQueryPredicate
case class FieldEqualsPredicate(field: String, equalsWhat: Any) extends AssassinQueryPredicate
case class AndPredicate(what: AssassinQueryPredicate, andWhat: AssassinQueryPredicate) extends AssassinQueryPredicate


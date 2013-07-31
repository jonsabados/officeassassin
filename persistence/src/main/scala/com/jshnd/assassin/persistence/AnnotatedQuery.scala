package com.jshnd.assassin.persistence

import java.lang.reflect.Field
import scala.annotation.tailrec
import com.jshnd.assassin.reflection._

trait AnnotatedQuery {

  def predicate(): AssassinQueryPredicate = buildPredicate(annotatedFields(this, classOf[QueryField]), new NoPredicate)

  @tailrec
  private def buildPredicate(fields: List[(Field, QueryField)], accu: AssassinQueryPredicate): AssassinQueryPredicate = {
    if(fields.isEmpty) accu
    else buildPredicate(fields.tail, predicate(fields.head, accu))
  }

  def predicate(element: (Field, QueryField), currentPredicate: AssassinQueryPredicate): AssassinQueryPredicate = {
    element._1.setAccessible(true)
    val value = element._1.get(this)
    value match {
      case o: Option[Object] => element._1.get(this) match {
        case None => currentPredicate
        case s: Some[Object] => foo(s.get, element._2, currentPredicate)
      }
      case _ => foo(element._1.get(this), element._2, currentPredicate)
    }
  }

  def foo(value: AnyRef, annotation: QueryField, currentPredicate: AssassinQueryPredicate): AssassinQueryPredicate = {
    currentPredicate match {
      case NoPredicate() => new FieldEqualsPredicate(annotation.value(), value)
      case _ => new AndPredicate(currentPredicate, new FieldEqualsPredicate(annotation.value(), value))
    }
  }
}

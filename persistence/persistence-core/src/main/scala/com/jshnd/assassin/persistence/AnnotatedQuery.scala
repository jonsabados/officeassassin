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
      case o: Option[Any] => value match {
        case None => currentPredicate
        case s: Some[Any] => predicate(s.get, element._2, currentPredicate)
      }
      case _ => predicate(element._1.get(this), element._2, currentPredicate)
    }
  }

  def predicate(value: Any, annotation: QueryField, currentPredicate: AssassinQueryPredicate): AssassinQueryPredicate = {
    currentPredicate match {
      case NoPredicate() => new FieldEqualsPredicate(annotation.value(), value)
      case _ => new AndPredicate(currentPredicate, new FieldEqualsPredicate(annotation.value(), value))
    }
  }
}

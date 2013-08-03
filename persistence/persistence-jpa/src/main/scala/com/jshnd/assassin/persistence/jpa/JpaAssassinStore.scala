package com.jshnd.assassin.persistence.jpa

import scala.collection.JavaConversions._
import com.jshnd.assassin.persistence._
import javax.persistence.{PersistenceContext, EntityManager}
import javax.persistence.criteria.{CriteriaQuery, Predicate, CriteriaBuilder, Root}
import com.jshnd.assassin.persistence.FieldEqualsPredicate
import javax.inject.Inject

class JpaAssassinStore @Inject() (mapFact: JpaTypeMapperFactory) extends AssassinStore {

  @Inject
  var em: EntityManager = _

  def find[T](query: AssassinQuery[T]): List[T] = {
    doFind(query)
  }

  def doFind[J, A](query: AssassinQuery[A]): List[A] = {
    val builder = new NonAmbiguousCriteriaBuilder(em.getCriteriaBuilder)
    val mapper: JpaMapper[J, A] = mapFact.mapper(query.forType)
    val criteria = builder.createQuery(mapper.jpaClass)
    val root: Root[J] = criteria.from(mapper.jpaClass)
    val jpaQuery = em.createQuery(applyPredicate(builder, root, criteria, query.predicate))
    val result = jpaQuery.setFirstResult(query.firstRecord).setMaxResults(query.lastRecord - query.firstRecord).getResultList
    result.map(mapper.map).toList
  }

  def applyPredicate[T](b: NonAmbiguousCriteriaBuilder,
                        r: Root[T], c: NonAmbiguousCriteriaQuery[T],
                        p: AssassinQueryPredicate): NonAmbiguousCriteriaQuery[T] = p match {
    case NoPredicate() => c
    case _ => c.where(toPredicate(b, r, p))
  }

  def toPredicate[T](b: NonAmbiguousCriteriaBuilder, r: Root[T], p: AssassinQueryPredicate): Predicate = p match {
    case FieldEqualsPredicate(field, equalsWhat) => b.equal(r.get(field), equalsWhat)
    case AndPredicate(left, right) => b.and(toPredicate(b, r, left), toPredicate(b, r, right))
    case _ => throw new UnsupportedOperationException("Please implement me: " + p)
  }

  implicit def unwrapAmbiguousHack[T](hack: NonAmbiguousCriteriaQuery[T]): CriteriaQuery[T] = hack.unwrap()

}

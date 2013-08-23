package com.jshnd.assassin.jpa

import scala.collection.JavaConversions._
import javax.persistence.EntityManager
import javax.persistence.criteria.{CriteriaQuery, Predicate, Root}
import javax.inject.Inject
import com.google.inject.persist.Transactional
import com.jshnd.assassin.query._
import com.jshnd.assassin.query.NoPredicate
import com.jshnd.assassin.persistence.jpa.{NonAmbiguousCriteriaQuery, NonAmbiguousCriteriaBuilder}
import com.google.inject.Provider

class JpaAssassinStore @Inject() (mapFact: JpaTypeMapperFactory, emp: Provider[EntityManager]) extends AssassinStore {

  def em = emp.get()

  @Transactional
  def persist[T](entity: T): T = {
    val mapper: JpaMapper[AnyRef, T] = mapFact.mapper(entity.getClass.asInstanceOf[Class[T]])
    val jpaEntity = mapper.mapToJpa(entity)
    em.persist(jpaEntity)
    mapper.mapToAssassin(jpaEntity)
  }

  def load[T](id: Any, clazz: Class[T]): Option[T] = {
    val mapper: JpaMapper[AnyRef, T] = mapFact.mapper(clazz)
    val entity = em.find(mapper.jpaClass, id)
    if(entity == null) None
    else Some(mapper.mapToAssassin(entity))
  }

  @Transactional
  def find[A](query: AssassinQuery[A]): List[A] = {
    doFind(query)
  }

  @Transactional
  def findUnique[T](query: AssassinQuery[T]): Option[T] = {
    val matches = find(query)
    if(matches.isEmpty) None
    else if(matches.tail.isEmpty) Some(matches.head)
    else throw new IllegalArgumentException("Query " + query + " had many matches")
  }

  private def doFind[J, A](query: AssassinQuery[A]): List[A] = {
    val builder = new NonAmbiguousCriteriaBuilder(em.getCriteriaBuilder)
    val mapper: JpaMapper[J, A] = mapFact.mapper(query.forType)
    val criteria = builder.createQuery(mapper.jpaClass)
    val root: Root[J] = criteria.from(mapper.jpaClass)
    val jpaQuery = em.createQuery(applyPredicate(builder, root, criteria, query.predicate))
    val result = jpaQuery.setFirstResult(query.firstRecord).setMaxResults(query.lastRecord - query.firstRecord).getResultList
    result.map(mapper.mapToAssassin).toList
  }

  private def applyPredicate[T](b: NonAmbiguousCriteriaBuilder,
                        r: Root[T], c: NonAmbiguousCriteriaQuery[T],
                        p: AssassinQueryPredicate): NonAmbiguousCriteriaQuery[T] = p match {
    case NoPredicate() => c
    case _ => c.where(toPredicate(b, r, p))
  }

  private def toPredicate[T](b: NonAmbiguousCriteriaBuilder, r: Root[T], p: AssassinQueryPredicate): Predicate = p match {
    case FieldEqualsPredicate(field, equalsWhat) => b.equal(r.get(field), equalsWhat)
    case AndPredicate(left, right) => b.and(toPredicate(b, r, left), toPredicate(b, r, right))
    case _ => throw new UnsupportedOperationException("Please implement me: " + p)
  }

  implicit def unwrapAmbiguousHack[T](hack: NonAmbiguousCriteriaQuery[T]): CriteriaQuery[T] = hack.unwrap()

}

package com.jshnd.assassin.persistence.jpa

import scala.collection.JavaConversions._
import com.jshnd.assassin.persistence._
import javax.persistence.{EntityManager}
import javax.persistence.criteria.{CriteriaQuery, Predicate, Root}
import com.jshnd.assassin.persistence.FieldEqualsPredicate
import javax.inject.Inject
import com.google.inject.persist.Transactional
import com.jshnd.assassin.HasAssassinConfiguration

class JpaAssassinStore @Inject() (mapFact: JpaTypeMapperFactory, em: EntityManager) extends AssassinStore with HasAssassinConfiguration {

  @Transactional
  def persist[A](entity: A) {
    val mapper = mapFact.mapper(entity.getClass.asInstanceOf[Class[A]])
    em.persist(mapper.mapToJpa(entity))
  }

  @Transactional
  def find[A](query: AssassinQuery[A]): List[A] = {
    doFind(query)
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

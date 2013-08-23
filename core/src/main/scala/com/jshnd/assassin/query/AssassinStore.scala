package com.jshnd.assassin.query

trait AssassinStore {

  def load[T](id: Any, clazz: Class[T]): Option[T]

  def find[T](query: AssassinQuery[T]): List[T]

  /**
   * Find a unique entity by query.
   * @param query the query to find with
   * @tparam T the entity type
   * @return Some[T] or None if not found
   * @throws IllegalArgumentException if the query results in many matches
   */
  def findUnique[T](query: AssassinQuery[T]): Option[T]

  /**
   * Persist an entity
   * @param entity entity to persist
   * @tparam T the entity type
   * @return the persisted entity, with any effects of persistence populated (such as the id)
   */
  def persist[T](entity: T): T

}

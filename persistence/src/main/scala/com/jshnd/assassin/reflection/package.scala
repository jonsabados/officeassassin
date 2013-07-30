package com.jshnd.assassin

import java.lang.annotation.Annotation
import java.lang.reflect.Field
import scala.annotation.tailrec

package object reflection {

  @tailrec
  def fields(forClass: Class[_ <: Object], accu: List[Field] = List()): List[Field] = {
    if (forClass == classOf[Object]) accu
    else fields(forClass.getSuperclass.asInstanceOf[Class[_ <: Object]], accu ::: forClass.getDeclaredFields.toList)
  }

  def fieldAnnotations(instance: AnyRef): List[(Field, Annotation)] = {
    (for(
      field <- fields(instance.getClass);
      annotation <- field.getAnnotations
    ) yield(field, annotation)).toList
  }

  def annotatedFields[T <: Annotation](instance: AnyRef, annotationClass: Class[T]): List[(Field, T)] = {
    fieldAnnotations(instance).filter(elem => elem._2.annotationType() == annotationClass).asInstanceOf[List[(Field, T)]]
  }

}

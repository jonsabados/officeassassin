package com.jshnd.assassin

import java.lang.annotation.Annotation
import java.lang.reflect.Field

package object reflection {

  def fieldAnnotations(instance: AnyRef): List[(Field, Annotation)] = {
    (for(
      field <- instance.getClass.getDeclaredFields;
      annotation <- field.getDeclaredAnnotations
    ) yield(field, annotation)).toList
  }

}

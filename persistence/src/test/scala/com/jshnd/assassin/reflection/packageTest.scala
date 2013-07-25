package com.jshnd.assassin.reflection

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import java.lang.annotation.Annotation
import java.lang.reflect.Field
import scala.annotation.meta.field
import scala.beans.BeanProperty

@RunWith(classOf[JUnitRunner])
class packageTest extends FunSpec {

  class TestInput(nonAnnotated: String, @BeanProperty @(TestAnnotation @field)(propA = "a", propB = "b") var annotated: String) {

    @TestAnnotationTwo
    @TestAnnotation(propA = "innerA", propB = "innerB")
    val innerProp = "inner"

    val nonAnnotatedInnerProp = "fail"

  }

  describe("fieldAnnotations") {

    val input = new TestInput("no", "yes")

    val data: List[(Field, Annotation)] = fieldAnnotations(input)

    def annotationToString(annotation: Annotation):String = annotation match {
        case a: TestAnnotation => "1" + a.propA() + a.propB()
        case _: TestAnnotationTwo => "2"
        case _ => fail("Unexpected annotation: " + annotation)
    }

    it("returns expected data") {
      val converted: Set[(String, String)] = data.map(x => (x._1.getName, annotationToString(x._2))).toSet
      assert(converted === Set(("annotated", "1ab"), ("innerProp", "1innerAinnerB"), ("innerProp", "2")))
    }

  }
//
//  describe("annotatedSymbols") {
//
//    val input = new TestInput("no", "yes")
//
//
//    it("Should return constructor fields") {
//
//    }
//
//    it("Should return non-consturctor fields") {
//
//    }
//
//  }

}

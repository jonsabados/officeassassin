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

  case class TestInput(nonAnnotated: String, @(TestAnnotation @field)(propA = "a", propB = "b") annotated: String) {

    @TestAnnotationTwo
    @TestAnnotation(propA = "innerA", propB = "innerB")
    val innerProp = "inner"

    val nonAnnotatedInnerProp = "fail"

  }

  class TestInputExtension(foo: String, propA: String) extends TestInput(foo, propA) {

  }

  def annotationToString(annotation: Annotation):String = annotation match {
    case a: TestAnnotation => "1" + a.propA() + a.propB()
    case _: TestAnnotationTwo => "2"
    case _ => fail("Unexpected annotation: " + annotation)
  }

  describe("fieldAnnotations") {

    def doFieldAnnotationsTest(input: TestInput) {
      val data: List[(Field, Annotation)] = fieldAnnotations(input)
      val converted: Set[(String, String)] = data.map(x => (x._1.getName, annotationToString(x._2))).toSet
      assert(converted === Set(("annotated", "1ab"), ("innerProp", "1innerAinnerB"), ("innerProp", "2")))
    }

    it("returns expected data") {
      val input = new TestInput("no", "yes")

      doFieldAnnotationsTest(input)
    }

    it("returns expected data with extension") {
      val input = new TestInputExtension("no", "yes")

      doFieldAnnotationsTest(input)
    }

  }

  describe("annotatedFields") {

    it("returns expected data") {
      val input = new TestInput("no", "yes")

      val data: List[(Field, TestAnnotation)] = annotatedFields(input, classOf[TestAnnotation])
      val converted: Set[(String, String)] = data.map(x => (x._1.getName, annotationToString(x._2))).toSet
      assert(converted === Set(("annotated", "1ab"), ("innerProp", "1innerAinnerB")))
    }

  }

}

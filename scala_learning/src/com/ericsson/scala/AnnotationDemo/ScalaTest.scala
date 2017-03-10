package com.ericsson.scala.AnnotationDemo

import org.junit.Test

class ScalaTest {
  @Test
  def test1() {
    print("test1")
  }

  @Test(timeout = 1L)
  def test2() {
    print("test2")
  }
}
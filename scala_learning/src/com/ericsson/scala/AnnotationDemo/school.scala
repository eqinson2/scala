package com.ericsson.scala.AnnotationDemo

@deprecated
class School[@deprecated T](@deprecated val name: String, @deprecated val city: String) {
  @deprecated val standard: Int = 0
  @deprecated var classes: String = _
  @deprecated def teach(@deprecated student: T) = {
    @deprecated var temp = 0
  }
}

object School{
  new School("1","2")
}
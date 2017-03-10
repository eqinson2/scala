package com.ericsson.scala.PackageImports

package com {
  class T1() {}
  package eqinson {
    class T2(t1: T1) {}
    package scala {
      class T3(t1: T1, t2: T2) {}
    }
  }
}

package com.eqinson.scala {
  // class T4(t1: T1, t2: T2, t3: T3) {}
}

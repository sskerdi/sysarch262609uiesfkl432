package sysarch.circuits.helpers

import sysarch.chisel._
import sysarch.gates._

class nBitAND(n: Int) extends Module {
  val a   = IO(Input(Vec(n, Bool())))
  val out = IO(Output(Bool()))

  if (n == 1) {
    out := a(0)
  } else {
    val firstGate = Module(new ANDGate())
    firstGate.a := a(0)
    firstGate.b := a(1)

    var res = firstGate.out

    for (i <- 2 until n) {
      val gate = Module(new ANDGate())
      gate.a := res
      gate.b := a(i)
      res = gate.out
    }

    out := res
  }
}

package sysarch.circuits.helpers

import sysarch.chisel._
import sysarch.gates._

class nBitNOT(n: Int) extends Module {
  val a   = IO(Input(Vec(n, Bool())))
  val out = IO(Output(Vec(n, Bool())))

  for (i <- 0 until n) {
    val gate = Module(new NOTGate())
    gate.a := a(i)
    out(i) := gate.out

  }

}

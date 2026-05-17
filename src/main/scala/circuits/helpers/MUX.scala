package sysarch.circuits.helpers

import sysarch.chisel._
import sysarch.gates._

class Mux(width: Int) extends Module {
  val a   = IO(Input(Vec(width, Bool())))
  val b   = IO(Input(Vec(width, Bool())))
  val sel = IO(Input(Bool()))
  val out = IO(Output(Vec(width, Bool())))

  for (i <- 0 until width) {
    val NotGate  = Module(new NOTGate())
    val AndGateA = Module(new ANDGate())
    val AndGateB = Module(new ANDGate())
    val OrGate   = Module(new ORGate())

    NotGate.a  := sel
    AndGateA.a := NotGate.out
    AndGateA.b := a(i)
    OrGate.a   := AndGateA.out
    AndGateB.a := b(i)
    AndGateB.b := sel
    OrGate.b   := AndGateB.out

    out(i) := OrGate.out

  }
}

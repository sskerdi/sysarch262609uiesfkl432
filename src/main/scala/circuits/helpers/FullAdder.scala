package sysarch.circuits.helpers

import sysarch.chisel._
import sysarch.gates._
import sysarch.circuits.helpers._

class FullAdder extends Module {
  val a    = IO(Input(Bool()))
  val b    = IO(Input(Bool()))
  val cin  = IO(Input(Bool()))
  val sum  = IO(Output(Bool()))
  val cout = IO(Output(Bool()))

  val ha1    = Module(new HalfAdder)
  val ha2    = Module(new HalfAdder)
  val OrGate = Module(new ORGate)

  ha1.a    := a
  ha1.b    := b
  ha2.a    := ha1.sum
  ha2.b    := cin
  sum      := ha2.sum
  OrGate.a := ha2.cout
  OrGate.b := ha1.cout

  cout := OrGate.out

}

package sysarch.circuits.helpers

import sysarch.chisel._
import sysarch.gates._
import sysarch.circuits.helpers._

class HalfAdder extends Module {
  val a    = IO(Input(Bool()))
  val b    = IO(Input(Bool()))
  val sum  = IO(Output(Bool()))
  val cout = IO(Output(Bool()))

  val XorGate = Module(new (XORGate))
  val AndGate = Module(new (ANDGate))

  XorGate.a := a
  XorGate.b := b
  sum       := XorGate.out
  AndGate.a := a
  AndGate.b := b
  cout      := AndGate.out

}

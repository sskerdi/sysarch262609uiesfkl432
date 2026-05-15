package sysarch.circuits.helpers

import sysarch.chisel._
import sysarch.gates._
import sysarch.circuits.helpers._

class nBitAdderSubtractor(width: Int) extends Module {
  val a          = IO(Input(Vec(width, Bool())))
  val b          = IO(Input(Vec(width, Bool())))
  val enable_sub = IO(Input(Bool()))
  val sum        = IO(Output(Vec(width, Bool())))
  val cout       = IO(Output(Bool()))

  val bXor = Wire(Vec(width, Bool()))
  for (i <- 0 until width) {
    val xorGate = Module(new XORGate)
    xorGate.a := b(i)
    xorGate.b := enable_sub
    bXor(i)   := xorGate.out
  }

  // Ripple-carry adder; enable_sub feeds as carry-in (+1 for two's complement)
  var carry: Bool = enable_sub
  for (i <- 0 until width) {
    val fa = Module(new FullAdder)
    fa.a   := a(i)
    fa.b   := bXor(i)
    fa.cin := carry
    sum(i) := fa.sum
    carry = fa.cout
  }

  // Addition:    cout = carry        (1 means overflow)
  // Subtraction: cout = NOT carry    (0 carry means a < b, i.e. underflow)
  // Both cases:  cout = carry XOR enable_sub
  val coutXor = Module(new XORGate)
  coutXor.a := carry
  coutXor.b := enable_sub
  cout      := coutXor.out
}

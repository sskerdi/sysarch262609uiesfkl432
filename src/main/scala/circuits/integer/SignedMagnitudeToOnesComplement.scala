package sysarch.circuits.integer

import sysarch.chisel._
import sysarch.gates._
import sysarch.circuits.helpers._

class SignedMagnitudeToOnesComplement(width: Int) extends Module {
  val signedMagnitude = IO(Input(Vec(width, Bool())))
  val onesComplement  = IO(Output(Vec(width, Bool())))

  onesComplement(width - 1) := signedMagnitude(width - 1)

  val bNot = Module(new nBitNOT(width - 1))
  bNot.a := signedMagnitude.slice(0, width - 1)

  for (i <- 0 until width - 1) {
    val mux = Module(new Mux(1))
    mux.sel           := signedMagnitude(width - 1)
    mux.a(0)          := signedMagnitude(i)
    mux.b(0)          := bNot.out(i)
    onesComplement(i) := mux.out(0)
  }
}

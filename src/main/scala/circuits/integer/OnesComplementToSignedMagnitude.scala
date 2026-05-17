package sysarch.circuits.integer
import sysarch.chisel._
import sysarch.gates._
import sysarch.circuits.helpers._

class OnesComplementToSignedMagnitude(width: Int) extends Module {
  val onesComplement  = IO(Input(Vec(width, Bool())))
  val signedMagnitude = IO(Output(Vec(width, Bool())))

  signedMagnitude(width - 1) := onesComplement(width - 1)

  val bNot = Module(new nBitNOT(width - 1))
  bNot.a := onesComplement.slice(0, width - 1)

  for (i <- 0 until width - 1) {
    val mux = Module(new Mux(1))
    mux.sel            := onesComplement(width - 1)
    mux.a(0)           := onesComplement(i)
    mux.b(0)           := bNot.out(i)
    signedMagnitude(i) := mux.out(0)
  }
}

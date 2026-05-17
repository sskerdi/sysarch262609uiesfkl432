package sysarch.circuits.helpers
import sysarch.chisel._
import sysarch.gates._

class nBitComparator(width: Int) extends Module {
  val a  = IO(Input(Vec(width, Bool())))
  val b  = IO(Input(Vec(width, Bool())))
  val gt = IO(Output(Bool()))
  val eq = IO(Output(Bool()))

  val bNot = Wire(Vec(width, Bool()))
  val notB = Module(new nBitNOT(width))
  notB.a := b
  bNot   := notB.out

  val gt_chain = Wire(Vec(width + 1, Bool()))
  val eq_chain = Wire(Vec(width + 1, Bool()))

  gt_chain(width) := false.B
  eq_chain(width) := true.B

  // Iterate from MSB
  for (i <- (0 until width).reverse) {
    val andGate = Module(new ANDGate())
    andGate.a := a(i)
    andGate.b := bNot(i)
    val a_gt_b_i = andGate.out // a(i) > b(i)

    val andGate2 = Module(new ANDGate())
    andGate2.a := eq_chain(i + 1)
    andGate2.b := a_gt_b_i
    val new_gt = andGate2.out

    val orGate = Module(new ORGate())
    orGate.a    := gt_chain(i + 1)
    orGate.b    := new_gt
    gt_chain(i) := orGate.out

    val xorGate = Module(new XORGate())
    xorGate.a := a(i)
    xorGate.b := b(i)
    val bits_differ = xorGate.out

    val notGate = Module(new NOTGate())
    notGate.a := bits_differ
    val bits_equal = notGate.out

    val andGate3 = Module(new ANDGate())
    andGate3.a  := eq_chain(i + 1)
    andGate3.b  := bits_equal
    eq_chain(i) := andGate3.out
  }

  gt := gt_chain(0)
  eq := eq_chain(0)
}

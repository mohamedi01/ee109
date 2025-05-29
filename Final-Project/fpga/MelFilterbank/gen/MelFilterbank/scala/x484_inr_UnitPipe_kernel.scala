import emul._
import emul.implicits._

/** BEGIN UnitPipe x484_inr_UnitPipe **/
object x484_inr_UnitPipe_kernel {
  def run(
    b464: Bool,
    x465: Ptr[FloatPoint],
    b463: FixedPoint
  ): Unit = if (b464){
    val x482 = x465.value
    val x483_wr = x362_outSram_0.update("MelFilterbank.scala:42:20", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b463), Seq(TRUE), Seq(x482))
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x484_inr_UnitPipe **/

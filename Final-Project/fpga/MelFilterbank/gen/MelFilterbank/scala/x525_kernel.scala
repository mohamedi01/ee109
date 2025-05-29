import emul._
import emul.implicits._

/** BEGIN ParallelPipe x525 **/
object x525_kernel {
  def run(
    x467_ctrchain: Array[Counterlike],
    b463: FixedPoint,
    x465: Ptr[FloatPoint],
    b464: Bool
  ): Unit = {
    val x481_inr_Foreach = x481_inr_Foreach_kernel.run(x467_ctrchain, b463, x465, b464)
    val x484_inr_UnitPipe = x484_inr_UnitPipe_kernel.run(b464, x465, b463)
  } 
}
/** END ParallelPipe x525 **/

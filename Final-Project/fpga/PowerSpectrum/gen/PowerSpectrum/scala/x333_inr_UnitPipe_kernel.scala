import emul._
import emul.implicits._

/** BEGIN UnitPipe x333_inr_UnitPipe **/
object x333_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x327_deq_x315 = {
      val a0 = if (Bool(true,true) && x315_fifo.nonEmpty) x315_fifo.dequeue() else Struct2(FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)))
      Array[Struct2](a0)
    }
    val x328_elem_0 = x327_deq_x315.apply(0)
    val x329_apply = x328_elem_0.end
    val x330_wr_x325 = if (TRUE) x325_reg.set(x329_apply)
    val x331_apply = x328_elem_0.size
    val x332_wr_x326 = if (TRUE) x326_reg.set(x331_apply)
  } 
}
/** END UnitPipe x333_inr_UnitPipe **/

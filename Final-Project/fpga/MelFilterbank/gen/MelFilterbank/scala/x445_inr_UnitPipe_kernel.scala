import emul._
import emul.implicits._

/** BEGIN UnitPipe x445_inr_UnitPipe **/
object x445_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x439_deq_x427 = {
      val a0 = if (Bool(true,true) && x427_fifo.nonEmpty) x427_fifo.dequeue() else Struct2(FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)))
      Array[Struct2](a0)
    }
    val x440_elem_0 = x439_deq_x427.apply(0)
    val x441_apply = x440_elem_0.end
    val x442_wr_x437 = if (TRUE) x437_reg.set(x441_apply)
    val x443_apply = x440_elem_0.size
    val x444_wr_x438 = if (TRUE) x438_reg.set(x443_apply)
  } 
}
/** END UnitPipe x445_inr_UnitPipe **/

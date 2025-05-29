import emul._
import emul.implicits._

/** BEGIN UnitPipe x405_inr_UnitPipe **/
object x405_inr_UnitPipe_kernel {
  def run(
    b393: Bool
  ): Unit = if (b393){
    val x397_deq_x364 = {
      val a0 = if (Bool(true,true) && x364_fifo.nonEmpty) x364_fifo.dequeue() else Struct2(FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)))
      Array[Struct2](a0)
    }
    val x398_elem_0 = x397_deq_x364.apply(0)
    val x399_apply = x398_elem_0.start
    val x400_wr_x394 = if (TRUE) x394_reg.set(x399_apply)
    val x401_apply = x398_elem_0.end
    val x402_wr_x395 = if (TRUE) x395_reg.set(x401_apply)
    val x403_apply = x398_elem_0.size
    val x404_wr_x396 = if (TRUE) x396_reg.set(x403_apply)
  } else null.asInstanceOf[Unit]
}
/** END UnitPipe x405_inr_UnitPipe **/

import emul._
import emul.implicits._

/** BEGIN UnitPipe x893_inr_UnitPipe **/
object x893_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x887_deq_x870 = {
      val a0 = if (Bool(true,true) && x870_fifo.nonEmpty) x870_fifo.dequeue() else Struct2(FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)))
      Array[Struct2](a0)
    }
    val x888_elem_0 = x887_deq_x870.apply(0)
    val x889_apply = x888_elem_0.end
    val x890_wr_x885 = if (TRUE) x885_reg.set(x889_apply)
    val x891_apply = x888_elem_0.size
    val x892_wr_x886 = if (TRUE) x886_reg.set(x891_apply)
  } 
}
/** END UnitPipe x893_inr_UnitPipe **/

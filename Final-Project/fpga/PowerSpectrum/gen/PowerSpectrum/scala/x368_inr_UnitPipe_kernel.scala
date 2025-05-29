import emul._
import emul.implicits._

/** BEGIN UnitPipe x368_inr_UnitPipe **/
object x368_inr_UnitPipe_kernel {
  def run(
  ): Unit = {
    val x362_deq_x350 = {
      val a0 = if (Bool(true,true) && x350_fifo.nonEmpty) x350_fifo.dequeue() else Struct2(FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)), FixedPoint.invalid(FixFormat(true,32,0)))
      Array[Struct2](a0)
    }
    val x363_elem_0 = x362_deq_x350.apply(0)
    val x364_apply = x363_elem_0.end
    val x365_wr_x360 = if (TRUE) x360_reg.set(x364_apply)
    val x366_apply = x363_elem_0.size
    val x367_wr_x361 = if (TRUE) x361_reg.set(x366_apply)
  } 
}
/** END UnitPipe x368_inr_UnitPipe **/

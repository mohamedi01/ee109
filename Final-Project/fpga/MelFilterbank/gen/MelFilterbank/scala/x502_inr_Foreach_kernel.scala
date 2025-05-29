import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x502_inr_Foreach **/
object x502_inr_Foreach_kernel {
  def run(
    x495_ctrchain: Array[Counterlike]
  ): Unit = {
    x495_ctrchain(0).foreach{case (is,vs) => 
      val b496 = is(0)
      val b497 = vs(0)
      val x498_rd = x362_outSram_0.apply("MelFilterbank.scala:44:15", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b496), Seq(b497))
      val x499_elem_0 = x498_rd.apply(0)
      val x500_tuple: Struct3 = new Struct3(x499_elem_0, Bool(true,true))
      val x501 = {
        if (b497) x487.enqueue(x500_tuple)
      }
    }
  } 
}
/** END UnrolledForeach x502_inr_Foreach **/

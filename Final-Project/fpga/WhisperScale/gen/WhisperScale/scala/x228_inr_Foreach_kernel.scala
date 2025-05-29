import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x228_inr_Foreach **/
object x228_inr_Foreach_kernel {
  def run(
    x221_ctrchain: Array[Counterlike]
  ): Unit = {
    x221_ctrchain(0).foreach{case (is,vs) => 
      val b222 = is(0)
      val b223 = vs(0)
      val x224_rd = x168_outSram_0.apply("WhisperScale.scala:49:15", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b222), Seq(b223))
      val x225_elem_0 = x224_rd.apply(0)
      val x226_tuple: Struct2 = new Struct2(x225_elem_0, Bool(true,true))
      val x227 = {
        if (b223) x213.enqueue(x226_tuple)
      }
    }
  } 
}
/** END UnrolledForeach x228_inr_Foreach **/

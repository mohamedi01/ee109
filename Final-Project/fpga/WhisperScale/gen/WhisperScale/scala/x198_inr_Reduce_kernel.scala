import emul._
import emul.implicits._

/** BEGIN UnrolledReduce x198_inr_Reduce **/
object x198_inr_Reduce_kernel {
  def run(
    x188_ctrchain: Array[Counterlike]
  ): Unit = {
    x188_ctrchain(0).foreach{case (is,vs) => 
      val b189 = is(0)
      val b190 = vs(0)
      val x191_rd = x167_inSram_0.apply("WhisperScale.scala:33:63", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b189), Seq(b190))
      val x192_elem_0 = x191_rd.apply(0)
      val x194 = b189 === FixedPoint(BigDecimal("0"),FixFormat(true,32,0))
      val x242 = {
        if (TRUE) {
          x186_currentMaxReg_0.set((if (x194) x192_elem_0 else Number.max(x186_currentMaxReg_0.value, x192_elem_0)))
        }
        x186_currentMaxReg_0.value
      }
    }
  } 
}
/** END UnrolledReduce x198_inr_Reduce **/

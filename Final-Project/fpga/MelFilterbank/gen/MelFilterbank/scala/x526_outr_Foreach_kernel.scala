import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x526_outr_Foreach **/
object x526_outr_Foreach_kernel {
  def run(
    x462_ctrchain: Array[Counterlike]
  ): Unit = {
    x462_ctrchain(0).foreach{case (is,vs) => 
      val b463 = is(0)
      val b464 = vs(0)
      val x465: Ptr[FloatPoint] = Ptr[FloatPoint](FloatPoint("-0.0", FltFormat(23,8)))
      val x466_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("201"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x467_ctrchain = Array[Counterlike](x466_ctr)
      val x525 = x525_kernel.run(x467_ctrchain, b463, x465, b464)
    }
  } 
}
/** END UnrolledForeach x526_outr_Foreach **/

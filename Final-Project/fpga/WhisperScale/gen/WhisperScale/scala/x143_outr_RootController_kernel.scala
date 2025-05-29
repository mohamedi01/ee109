import emul._
import emul.implicits._

/** BEGIN AccelScope x143_outr_RootController **/
object x143_outr_RootController_kernel {
  def run(
  ): Unit = {
    try {
      val x185_outr_UnitPipe_DenseTransfer = x185_outr_UnitPipe_DenseTransfer_kernel.run()
      x186_currentMaxReg_0.initMem(FloatPoint(BigDecimal("-1000.000000"), FltFormat(23,8)))
      val x187_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x188_ctrchain = Array[Counterlike](x187_ctr)
      val x198_inr_Reduce = x198_inr_Reduce_kernel.run(x188_ctrchain)
      val x199_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x200_ctrchain = Array[Counterlike](x199_ctr)
      val x211_inr_Foreach = x211_inr_Foreach_kernel.run(x200_ctrchain)
      val x233_outr_UnitPipe_DenseTransfer = x233_outr_UnitPipe_DenseTransfer_kernel.run()
    }
    catch {
      case x: Exception if x.getMessage == "exit" =>  
      case t: Throwable => throw t
    }
  } 
}
/** END AccelScope x143_outr_RootController **/

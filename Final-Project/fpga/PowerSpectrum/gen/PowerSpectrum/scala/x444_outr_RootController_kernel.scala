import emul._
import emul.implicits._

/** BEGIN AccelScope x444_outr_RootController **/
object x444_outr_RootController_kernel {
  def run(
  ): Unit = {
    try {
      val x443 = x443_kernel.run()
      val x384_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("338283"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x385_ctrchain = Array[Counterlike](x384_ctr)
      val x396_inr_Foreach = x396_inr_Foreach_kernel.run(x385_ctrchain)
      val x429_outr_UnitPipe_DenseTransfer = x429_outr_UnitPipe_DenseTransfer_kernel.run()
    }
    catch {
      case x: Exception if x.getMessage == "exit" =>  
      case t: Throwable => throw t
    }
  } 
}
/** END AccelScope x444_outr_RootController **/

import emul._
import emul.implicits._

/** BEGIN AccelScope x527_outr_RootController **/
object x527_outr_RootController_kernel {
  def run(
  ): Unit = {
    try {
      val x524 = x524_kernel.run()
      val x461_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("80"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x462_ctrchain = Array[Counterlike](x461_ctr)
      val x526_outr_Foreach = x526_outr_Foreach_kernel.run(x462_ctrchain)
      val x507_outr_UnitPipe_DenseTransfer = x507_outr_UnitPipe_DenseTransfer_kernel.run()
    }
    catch {
      case x: Exception if x.getMessage == "exit" =>  
      case t: Throwable => throw t
    }
  } 
}
/** END AccelScope x527_outr_RootController **/

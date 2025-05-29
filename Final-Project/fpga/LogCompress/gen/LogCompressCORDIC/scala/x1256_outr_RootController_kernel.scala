import emul._
import emul.implicits._

/** BEGIN AccelScope x1256_outr_RootController **/
object x1256_outr_RootController_kernel {
  def run(
  ): Unit = {
    try {
      val x1253 = x1253_kernel.run()
      val x1243_rd_x856 = x856_Accel_n.value
      val x947_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x1243_rd_x856, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x948_ctrchain = Array[Counterlike](x947_ctr)
      val x1255_outr_Foreach = x1255_outr_Foreach_kernel.run(x948_ctrchain)
      val x1161_outr_UnitPipe_DenseTransfer = x1161_outr_UnitPipe_DenseTransfer_kernel.run()
      x1162_logMaxReg_0.initMem(FloatPoint(BigDecimal("-1000.000000"), FltFormat(23,8)))
      val x1247_rd_x856 = x856_Accel_n.value
      val x1163_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x1247_rd_x856, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x1164_ctrchain = Array[Counterlike](x1163_ctr)
      val x1175_inr_Reduce = x1175_inr_Reduce_kernel.run(x1164_ctrchain)
      val x1248_rd_x856 = x856_Accel_n.value
      val x1176_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), x1248_rd_x856, FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x1177_ctrchain = Array[Counterlike](x1176_ctr)
      val x1187_inr_Foreach = x1187_inr_Foreach_kernel.run(x1177_ctrchain)
      val x1225_outr_UnitPipe_DenseTransfer = x1225_outr_UnitPipe_DenseTransfer_kernel.run()
    }
    catch {
      case x: Exception if x.getMessage == "exit" =>  
      case t: Throwable => throw t
    }
  } 
}
/** END AccelScope x1256_outr_RootController **/

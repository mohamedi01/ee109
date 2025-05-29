import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x1255_outr_Foreach **/
object x1255_outr_Foreach_kernel {
  def run(
    x948_ctrchain: Array[Counterlike]
  ): Unit = {
    x948_ctrchain(0).foreach{case (is,vs) => 
      val b949 = is(0)
      val b950 = vs(0)
      x951_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      val x959_inr_UnitPipe = x959_inr_UnitPipe_kernel.run(b950, b949)
      x960_decade_calc_reg_0.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x961_reg.initMem(Bool(false,true))
      x962_reg.initMem(Bool(false,true))
      x963_reg.initMem(Bool(false,true))
      x964_reg.initMem(Bool(false,true))
      x965_reg.initMem(Bool(false,true))
      x966_reg.initMem(Bool(false,true))
      x967_reg.initMem(Bool(false,true))
      x968_reg.initMem(Bool(false,true))
      x969_reg.initMem(Bool(false,true))
      x970_reg.initMem(Bool(false,true))
      x971_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      val x1006_inr_UnitPipe = x1006_inr_UnitPipe_kernel.run(b950)
      x1007_norm_val_calc_reg_0.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1008_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1043_found_idx_0.initMem(FixedPoint(BigDecimal("126"),FixFormat(true,32,0)))
      x1044_found_flag_0.initMem(Bool(false,true))
      val x1254 = x1254_kernel.run(b950)
      val x1047_ctr = Counter(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)), FixedPoint(BigDecimal("127"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)), FixedPoint(BigDecimal("1"),FixFormat(true,32,0)))
      val x1048_ctrchain = Array[Counterlike](x1047_ctr)
      val x1065_inr_Foreach = x1065_inr_Foreach_kernel.run(x1048_ctrchain, b950)
      x1066_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1067_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1068_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1069_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      val x1084_inr_UnitPipe = x1084_inr_UnitPipe_kernel.run(b950)
      x1085_log10_of_norm_val_0.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1086_reg.initMem(FloatPoint("-0.0", FltFormat(23,8)))
      x1087_reg.initMem(Bool(false,true))
      x1088_reg.initMem(Bool(false,true))
      val x1097_inr_UnitPipe = x1097_inr_UnitPipe_kernel.run(b950)
      val x1244_rd_x1087 = x1087_reg.value
      val x1245_rd_x1088 = x1088_reg.value
      val x1117_inr_Switch = x1117_inr_Switch_kernel.run(x1245_rd_x1088, x1244_rd_x1087)
      val x1122_inr_UnitPipe = x1122_inr_UnitPipe_kernel.run(b949, b950)
    }
  } 
}
/** END UnrolledForeach x1255_outr_Foreach **/

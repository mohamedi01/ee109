import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x481_inr_Foreach **/
object x481_inr_Foreach_kernel {
  def run(
    x467_ctrchain: Array[Counterlike],
    b463: FixedPoint,
    x465: Ptr[FloatPoint],
    b464: Bool
  ): Unit = if (b464){
    x467_ctrchain(0).foreach{case (is,vs) => 
      val b468 = is(0)
      val b469 = vs(0)
      val x470 = x465.value
      val x522 = (b463 * FixedPoint(BigDecimal("201"),FixFormat(true,32,0))) + b468
      val x474_rd = x360_matSram_0.apply("MelFilterbank.scala:40:30", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(x522), Seq(b469 & b464))
      val x475_elem_0 = x474_rd.apply(0)
      val x476_rd = x361_vecSram_0.apply("MelFilterbank.scala:40:45", Seq(Seq(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))), Seq(b468), Seq(b469 & b464))
      val x477_elem_0 = x476_rd.apply(0)
      val x523 = (x475_elem_0 * x477_elem_0) + x470
      val x480 = { x465.set(x523) }
    }
  } else null.asInstanceOf[Unit]
}
/** END UnrolledForeach x481_inr_Foreach **/

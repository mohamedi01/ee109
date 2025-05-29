import emul._
import emul.implicits._

/** BEGIN UnrolledForeach x388_inr_Foreach **/
object x388_inr_Foreach_kernel {
  def run(
    x367_ctrchain: Array[Counterlike]
  ): Unit = {
    x367_ctrchain(0).foreach{case (is,vs) => 
      val b368 = is(0)
      val b369 = vs(0)
      val x370_mul = b368 * FixedPoint(BigDecimal("201"),FixFormat(true,32,0))
      val x371 = x370_mul >> FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
      val x372 = x371 << FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
      val x519 = x371 << FixedPoint(BigDecimal("6"),FixFormat(true,16,0))
      val x374_sub = x370_mul - x372
      val x375_sum = x374_sub + FixedPoint(BigDecimal("201"),FixFormat(true,32,0))
      val x376_sum = x374_sub + FixedPoint(BigDecimal("216"),FixFormat(true,32,0))
      val x377 = x376_sum >> FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
      val x378 = x377 << FixedPoint(BigDecimal("4"),FixFormat(true,16,0))
      val x520 = x377 << FixedPoint(BigDecimal("6"),FixFormat(true,16,0))
      val x380 = x519.toFixedPoint(FixFormat(true,64,0))
      val x381 = FixedPoint.fromInt(0)
      val x382_sum = x380 + x381
      val x383_tuple: Struct1 = new Struct1(x382_sum, x520, Bool(true,true))
      val x384 = true
      val x385 = {
        if (x384 & b369) x363.enqueue(x383_tuple)
      }
      val x386_tuple: Struct2 = new Struct2(x378, x374_sub, x375_sum)
      val x387_enq_x364 = {
        if (Bool(true,true) & b369) x364_fifo.enqueue(x386_tuple)
      }
    }
  } 
}
/** END UnrolledForeach x388_inr_Foreach **/

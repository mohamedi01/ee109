import emul._
import emul.implicits._

object x362_outSram_0 extends BankedMemory(
  name  = "outSram_0 (x362)",
  dims  = Seq(80),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(80)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

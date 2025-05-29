import emul._
import emul.implicits._

object x864_xLutSram_0 extends BankedMemory(
  name  = "xLutSram_0 (x864)",
  dims  = Seq(128),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(128)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

import emul._
import emul.implicits._

object x867_yLutSram_0 extends BankedMemory(
  name  = "yLutSram_0 (x867)",
  dims  = Seq(128),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(128)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

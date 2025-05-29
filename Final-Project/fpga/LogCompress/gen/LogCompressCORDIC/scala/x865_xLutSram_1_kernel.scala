import emul._
import emul.implicits._

object x865_xLutSram_1 extends BankedMemory(
  name  = "xLutSram_1 (x865)",
  dims  = Seq(128),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(128)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

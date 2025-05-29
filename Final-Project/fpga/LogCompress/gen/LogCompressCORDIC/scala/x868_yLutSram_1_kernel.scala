import emul._
import emul.implicits._

object x868_yLutSram_1 extends BankedMemory(
  name  = "yLutSram_1 (x868)",
  dims  = Seq(128),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(128)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

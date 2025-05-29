import emul._
import emul.implicits._

object x946_outSram_0 extends BankedMemory(
  name  = "outSram_0 (x946)",
  dims  = Seq(400000),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(400000)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

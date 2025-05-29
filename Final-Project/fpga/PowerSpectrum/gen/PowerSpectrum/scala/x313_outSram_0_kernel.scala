import emul._
import emul.implicits._

object x313_outSram_0 extends BankedMemory(
  name  = "outSram_0 (x313)",
  dims  = Seq(384312),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(384312)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

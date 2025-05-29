import emul._
import emul.implicits._

object x167_inSram_0 extends BankedMemory(
  name  = "inSram_0 (x167)",
  dims  = Seq(80),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(80)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

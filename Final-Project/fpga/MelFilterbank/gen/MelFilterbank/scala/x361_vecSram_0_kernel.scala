import emul._
import emul.implicits._

object x361_vecSram_0 extends BankedMemory(
  name  = "vecSram_0 (x361)",
  dims  = Seq(201),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(201)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

import emul._
import emul.implicits._

object x360_matSram_0 extends BankedMemory(
  name  = "matSram_0 (x360)",
  dims  = Seq(80,201),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(16080)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

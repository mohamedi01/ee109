import emul._
import emul.implicits._

object x312_imagSram_0 extends BankedMemory(
  name  = "imagSram_0 (x312)",
  dims  = Seq(384312),
  banks = Seq(1),
  data  = Array.fill(1){ Array.fill(384312)(FloatPoint.invalid(FltFormat(23,8))) },
  invalid = FloatPoint.invalid(FltFormat(23,8)),
  saveInit = false
)

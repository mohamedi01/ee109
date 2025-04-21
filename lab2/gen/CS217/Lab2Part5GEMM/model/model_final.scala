package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x810 = new ControllerModel(810, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x810", "36", "Accel {", "x810 = AccelScope(Block(Const(())))"))
    val x911 = CtrModel(0, Ask(880, "ArgIn x880 (ctr stop)", Ctx("x911", "37", "Foreach(K by tileK){kk =>", "x911 = CounterNew(Const(0),x1305,Const(16),Const(1))")), 16, 1)
    val x912 = CChainModel(List[CtrModel[_,_,_,_]](x911), Ctx("x912", "37", "Foreach(K by tileK){kk =>", "List(x911 = CounterNew(Const(0),x1305,Const(16),Const(1)))"))
    val x1251 = new ControllerModel(1251, OuterControl, Left(Pipelined), x912, 0, 1, Ctx("x1251", "37", "Foreach(K by tileK){kk =>", "x1251 = UnrolledForeach(Set(),x912,Block(Const(())),List(List(b913)),List(List(b914)),None)"))
    val x921 = new ControllerModel(921, InnerControl, Left(Sequenced), CChainModel(Seq()), 2, 1, Ctx("x921", "189", "Pipe {", "x921 = UnitPipe(Set(b914),Block(Const(())),None)"))
    val x922 = CtrModel(0, Ask(878, "ArgIn x878 (ctr stop)", Ctx("x922", "39", "Foreach(M by tileM){mm =>", "x922 = CounterNew(Const(0),x1306,Const(16),Const(1))")), 16, 1)
    val x923 = CChainModel(List[CtrModel[_,_,_,_]](x922), Ctx("x923", "39", "Foreach(M by tileM){mm =>", "List(x922 = CounterNew(Const(0),x1306,Const(16),Const(1)))"))
    val x1250 = new ControllerModel(1250, OuterControl, Left(Pipelined), x923, 0, 1, Ctx("x1250", "39", "Foreach(M by tileM){mm =>", "x1250 = UnrolledForeach(Set(b914),x923,Block(Const(())),List(List(b924)),List(List(b925)),None)"))
    val x932 = new ControllerModel(932, InnerControl, Left(Sequenced), CChainModel(Seq()), 2, 1, Ctx("x932", "189", "Pipe {", "x932 = UnitPipe(Set(b925, b914),Block(Const(())),None)"))
    val x1001_ctrlast = CtrModel(0, Ask(54, "length of dim #last", Ctx("x1001_ctrlast", "42", "tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)", "x1001 = UnitPipe(Set(b925, b914),Block(Const(())),None)")), 1, 1)
    val x1001 = new ControllerModel(1001, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x1001_ctrlast))), 0, 1, Ctx("x1001", "42", "tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)", "x1001 = UnitPipe(Set(b925, b914),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x1002 = CtrModel(0, Ask(879, "ArgIn x879 (ctr stop)", Ctx("x1002", "43", "Foreach(N by tileN){nn =>", "x1002 = CounterNew(Const(0),x1310,Const(16),Const(1))")), 16, 1)
    val x1003 = CChainModel(List[CtrModel[_,_,_,_]](x1002), Ctx("x1003", "43", "Foreach(N by tileN){nn =>", "List(x1002 = CounterNew(Const(0),x1310,Const(16),Const(1)))"))
    val x1335 = new ControllerModel(1335, OuterControl, Left(Pipelined), x1003, 0, 1, Ctx("x1335", "43", "Foreach(N by tileN){nn =>", "x1335 = UnrolledForeach(Set(b925, b914),x1003,Block(Const(())),List(List(b1004)),List(List(b1005)),None)"))
    val x1015 = new ControllerModel(1015, InnerControl, Left(Sequenced), CChainModel(Seq()), 2, 1, Ctx("x1015", "189", "Pipe {", "x1015 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)"))
    val x1334 = new ControllerModel(1334, OuterControl, Left(ForkJoin), CChainModel(Seq()), 0, 0, Ctx("x1334", "82", "stage(ParallelPipe(scala.collection.immutable.Set[Bit](), stageBlock(bundledStms.foreach(visit))))", "x1334 = ParallelPipe(Set(),Block(Const(())))"))
    val x1085_ctrlast = CtrModel(0, Ask(73, "length of dim #last", Ctx("x1085_ctrlast", "47", "tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM", "x1085 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)")), 1, 1)
    val x1085 = new ControllerModel(1085, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x1085_ctrlast))), 0, 1, Ctx("x1085", "47", "tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM", "x1085 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x1153_ctrlast = CtrModel(0, Ask(73, "length of dim #last", Ctx("x1153_ctrlast", "48", "tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM", "x1153 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)")), 1, 1)
    val x1153 = new ControllerModel(1153, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x1153_ctrlast))), 0, 1, Ctx("x1153", "48", "tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM", "x1153 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x1334.registerChild(x1085)
    x1334.registerChild(x1153)
    val x1154 = CtrModel(0, Ask(1317, "ctr stop", Ctx("x1154", "51", "Foreach(numel_m by 1) { m =>", "x1154 = CounterNew(Const(0),x1317,Const(1),Const(1))")), 1, 1)
    val x1155 = CChainModel(List[CtrModel[_,_,_,_]](x1154), Ctx("x1155", "51", "Foreach(numel_m by 1) { m =>", "List(x1154 = CounterNew(Const(0),x1317,Const(1),Const(1)))"))
    val x1188 = new ControllerModel(1188, OuterControl, Left(Sequenced), x1155, 0, 1, Ctx("x1188", "51", "Foreach(numel_m by 1) { m =>", "x1188 = UnrolledForeach(Set(b1005, b925, b914),x1155,Block(Const(())),List(List(b1156)),List(List(b1157)),None)"))
    val x1159 = CtrModel(0, Ask(1318, "ctr stop", Ctx("x1159", "52", "Foreach(numel_n by 1) { n =>", "x1159 = CounterNew(Const(0),x1318,Const(1),Const(1))")), 1, 1)
    val x1160 = CChainModel(List[CtrModel[_,_,_,_]](x1159), Ctx("x1160", "52", "Foreach(numel_n by 1) { n =>", "List(x1159 = CounterNew(Const(0),x1318,Const(1),Const(1)))"))
    val x1187 = new ControllerModel(1187, OuterControl, Left(Sequenced), x1160, 0, 1, Ctx("x1187", "52", "Foreach(numel_n by 1) { n =>", "x1187 = UnrolledForeach(Set(b1157, b1005, b925, b914),x1160,Block(Const(())),List(List(b1161)),List(List(b1162)),None)"))
    val x1164 = CtrModel(0, Ask(1319, "ctr stop", Ctx("x1164", "53", "Foreach(numel_k by 1) { k =>", "x1164 = CounterNew(Const(0),x1319,Const(1),Const(1))")), 1, 1)
    val x1165 = CChainModel(List[CtrModel[_,_,_,_]](x1164), Ctx("x1165", "53", "Foreach(numel_k by 1) { k =>", "List(x1164 = CounterNew(Const(0),x1319,Const(1),Const(1)))"))
    val x1186 = new ControllerModel(1186, InnerControl, Left(Pipelined), x1165, 11, 4, Ctx("x1186", "53", "Foreach(numel_k by 1) { k =>", "x1186 = UnrolledForeach(Set(b1005, b925, b1162, b1157, b914),x1165,Block(Const(())),List(List(b1166)),List(List(b1167)),None)"))
    x1187.registerChild(x1186)
    x1188.registerChild(x1187)
    val x1248_ctrlast = CtrModel(0, Ask(73, "length of dim #last", Ctx("x1248_ctrlast", "58", "c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM", "x1248 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)")), 1, 1)
    val x1248 = new ControllerModel(1248, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x1248_ctrlast))), 0, 1, Ctx("x1248", "58", "c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM", "x1248 = UnitPipe(Set(b1005, b925, b914),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x1335.registerChild(x1015)
    x1335.registerChild(x1334)
    x1335.registerChild(x1188)
    x1335.registerChild(x1248)
    x1250.registerChild(x932)
    x1250.registerChild(x1001)
    x1250.registerChild(x1335)
    x1251.registerChild(x921)
    x1251.registerChild(x1250)
    x810.registerChild(x1251)
    return x810
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part5GEMM///results_final")
    if (args.size >= 1 && (args.contains("noninteractive") || args.contains("ni"))) {
        interactive = false
        val idx = {0 max args.indexOf("noninteractive")} + {0 max args.indexOf("ni")}
        cliParams = args.drop(idx+1).takeWhile{_ != "tune"}.map(_.toInt)
        emit(s"Noninteractive Args: ${cliParams.mkString(" ")}") 
    }
    else {
      println(s"Suggested args: List()")
    }
    val allTuneParams: Seq[Map[String, Any]] = if (args.size >= 1 && (args.contains("tune"))) {
        retune = true
        val indices: Seq[Int] = args.zipWithIndex.filter(_._1 == "tune").map(_._2)
        indices.map{idx => args.drop(idx+1).takeWhile{x => x != "noninteractive" && x != "ni" && x != "tune"}.grouped(2).map{x => (x(0) -> {try {x(1).toInt} catch {case _: Throwable => x(1)}} )}.toMap}
    } else {Seq(Map[String, Any]())}
    isFinal = true
    val root = build_model()
    root.initializeAskMap(AskMap.map)
    root.loadPreviousAskMap(PreviousAskMap.map) // Load previous run's askmap
    emit(s"[final] Structure for app Lab2Part5GEMM")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app Lab2Part5GEMM")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part5GEMM///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App Lab2Part5GEMM: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x106 = new ControllerModel(106, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x106", "113", "Accel {", "x106 = AccelScope(Block(Const(())))"))
    val x49 = CtrModel(0, Ask(3, "ArgIn x3 (ctr stop)", Ctx("x49", "114", "Foreach(K by tileK){kk =>", "x49 = CounterNew(Const(0),x48,Const(16),Const(1))")), 16, 1)
    val x51 = CChainModel(List[CtrModel[_,_,_,_]](x49), Ctx("x51", "114", "Foreach(K by tileK){kk =>", "List(x49 = CounterNew(Const(0),x48,Const(16),Const(1)))"))
    val x105 = new ControllerModel(105, OuterControl, Left(Pipelined), x51, 0, 1, Ctx("x105", "114", "Foreach(K by tileK){kk =>", "x105 = OpForeach(Set(),x51,Block(Const(())),List(b50),None)"))
    val x56 = CtrModel(0, Ask(1, "ArgIn x1 (ctr stop)", Ctx("x56", "116", "Foreach(M by tileM){mm =>", "x56 = CounterNew(Const(0),x55,Const(16),Const(1))")), 16, 1)
    val x58 = CChainModel(List[CtrModel[_,_,_,_]](x56), Ctx("x58", "116", "Foreach(M by tileM){mm =>", "List(x56 = CounterNew(Const(0),x55,Const(16),Const(1)))"))
    val x104 = new ControllerModel(104, OuterControl, Left(Pipelined), x58, 0, 1, Ctx("x104", "116", "Foreach(M by tileM){mm =>", "x104 = OpForeach(Set(),x58,Block(Const(())),List(b57),None)"))
    val x182 = CtrModel(0, Ask(61, "ctr stop", Ctx("x182", "119", "tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)", "x182 = CounterNew(Const(0),x61,Const(1),Const(1))")), 1, 1)
    val x184 = CChainModel(List[CtrModel[_,_,_,_]](x182), Ctx("x184", "119", "tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)", "List(x182 = CounterNew(Const(0),x61,Const(1),Const(1)))"))
    val x242_ctrlast = CtrModel(0, Ask(54, "length of dim #last", Ctx("x242_ctrlast", "119", "tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)", "x242 = OpForeach(Set(),x184,Block(Const(())),List(b183),None)")), 1, 1)
    val x242 = new ControllerModel(242, OuterControl, Left(DenseLoad), List(x184, CChainModel(List(x242_ctrlast))), 0, 1, Ctx("x242", "119", "tileA_sram load a(mm::mm+numel_m, kk::kk+numel_k)", "x242 = OpForeach(Set(),x184,Block(Const(())),List(b183),None)"), bitsPerCycle = 32.toDouble)
    val x68 = CtrModel(0, Ask(2, "ArgIn x2 (ctr stop)", Ctx("x68", "120", "Foreach(N by tileN){nn =>", "x68 = CounterNew(Const(0),x67,Const(16),Const(1))")), 16, 1)
    val x70 = CChainModel(List[CtrModel[_,_,_,_]](x68), Ctx("x70", "120", "Foreach(N by tileN){nn =>", "List(x68 = CounterNew(Const(0),x67,Const(16),Const(1)))"))
    val x103 = new ControllerModel(103, OuterControl, Left(Pipelined), x70, 0, 1, Ctx("x103", "120", "Foreach(N by tileN){nn =>", "x103 = OpForeach(Set(),x70,Block(Const(())),List(b69),None)"))
    val x248 = CtrModel(0, Ask(54, "ctr stop", Ctx("x248", "124", "tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM", "x248 = CounterNew(Const(0),x54,Const(1),Const(1))")), 1, 1)
    val x250 = CChainModel(List[CtrModel[_,_,_,_]](x248), Ctx("x250", "124", "tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM", "List(x248 = CounterNew(Const(0),x54,Const(1),Const(1)))"))
    val x308_ctrlast = CtrModel(0, Ask(73, "length of dim #last", Ctx("x308_ctrlast", "124", "tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM", "x308 = OpForeach(Set(),x250,Block(Const(())),List(b249),None)")), 1, 1)
    val x308 = new ControllerModel(308, OuterControl, Left(DenseLoad), List(x250, CChainModel(List(x308_ctrlast))), 0, 1, Ctx("x308", "124", "tileB_sram load b(kk::kk+numel_k, nn::nn+numel_n) // DRAM -> SRAM", "x308 = OpForeach(Set(),x250,Block(Const(())),List(b249),None)"), bitsPerCycle = 32.toDouble)
    val x311 = CtrModel(0, Ask(61, "ctr stop", Ctx("x311", "125", "tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM", "x311 = CounterNew(Const(0),x61,Const(1),Const(1))")), 1, 1)
    val x313 = CChainModel(List[CtrModel[_,_,_,_]](x311), Ctx("x313", "125", "tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM", "List(x311 = CounterNew(Const(0),x61,Const(1),Const(1)))"))
    val x371_ctrlast = CtrModel(0, Ask(73, "length of dim #last", Ctx("x371_ctrlast", "125", "tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM", "x371 = OpForeach(Set(),x313,Block(Const(())),List(b312),None)")), 1, 1)
    val x371 = new ControllerModel(371, OuterControl, Left(DenseLoad), List(x313, CChainModel(List(x371_ctrlast))), 0, 1, Ctx("x371", "125", "tileC_sram load c(mm::mm+numel_m, nn::nn+numel_n) // DRAM -> SRAM", "x371 = OpForeach(Set(),x313,Block(Const(())),List(b312),None)"), bitsPerCycle = 32.toDouble)
    val x83 = CtrModel(0, Ask(61, "ctr stop", Ctx("x83", "130", "Foreach(numel_m by 1 par 4) { m =>", "x83 = CounterNew(Const(0),x61,Const(1),Const(4))")), 1, 4)
    val x85 = CChainModel(List[CtrModel[_,_,_,_]](x83), Ctx("x85", "130", "Foreach(numel_m by 1 par 4) { m =>", "List(x83 = CounterNew(Const(0),x61,Const(1),Const(4)))"))
    val x100 = new ControllerModel(100, OuterControl, Left(Sequenced), x85, 0, 1, Ctx("x100", "130", "Foreach(numel_m by 1 par 4) { m =>", "x100 = OpForeach(Set(),x85,Block(Const(())),List(b84),None)"))
    val x86 = CtrModel(0, Ask(73, "ctr stop", Ctx("x86", "131", "Foreach(numel_n by 1 par 4) { n =>", "x86 = CounterNew(Const(0),x73,Const(1),Const(4))")), 1, 4)
    val x88 = CChainModel(List[CtrModel[_,_,_,_]](x86), Ctx("x88", "131", "Foreach(numel_n by 1 par 4) { n =>", "List(x86 = CounterNew(Const(0),x73,Const(1),Const(4)))"))
    val x99 = new ControllerModel(99, OuterControl, Left(Sequenced), x88, 0, 1, Ctx("x99", "131", "Foreach(numel_n by 1 par 4) { n =>", "x99 = OpForeach(Set(),x88,Block(Const(())),List(b87),None)"))
    val x89 = CtrModel(0, Ask(54, "ctr stop", Ctx("x89", "132", "Foreach(numel_k by 1) { k =>", "x89 = CounterNew(Const(0),x54,Const(1),Const(1))")), 1, 1)
    val x91 = CChainModel(List[CtrModel[_,_,_,_]](x89), Ctx("x91", "132", "Foreach(numel_k by 1) { k =>", "List(x89 = CounterNew(Const(0),x54,Const(1),Const(1)))"))
    val x98 = new ControllerModel(98, InnerControl, Left(Pipelined), x91, 14, 8, Ctx("x98", "132", "Foreach(numel_k by 1) { k =>", "x98 = OpForeach(Set(),x91,Block(Const(())),List(b90),None)"))
    x99.registerChild(x98)
    x100.registerChild(x99)
    val x372 = CtrModel(0, Ask(61, "ctr stop", Ctx("x372", "137", "c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM", "x372 = CounterNew(Const(0),x61,Const(1),Const(1))")), 1, 1)
    val x374 = CChainModel(List[CtrModel[_,_,_,_]](x372), Ctx("x374", "137", "c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM", "List(x372 = CounterNew(Const(0),x61,Const(1),Const(1)))"))
    val x429_ctrlast = CtrModel(0, Ask(73, "length of dim #last", Ctx("x429_ctrlast", "137", "c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM", "x429 = OpForeach(Set(),x374,Block(Const(())),List(b373),None)")), 1, 1)
    val x429 = new ControllerModel(429, OuterControl, Left(GatedDenseStore), List(x374, CChainModel(List(x429_ctrlast))), 0, 1, Ctx("x429", "137", "c(mm::mm+numel_m, nn::nn+numel_n) store tileC_sram // SRAM -> DRAM", "x429 = OpForeach(Set(),x374,Block(Const(())),List(b373),None)"), bitsPerCycle = 32.toDouble)
    x103.registerChild(x308)
    x103.registerChild(x371)
    x103.registerChild(x100)
    x103.registerChild(x429)
    x104.registerChild(x242)
    x104.registerChild(x103)
    x105.registerChild(x104)
    x106.registerChild(x105)
    return x106
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part6GEMM///results_dse")
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
    val root = build_model()
    root.initializeAskMap(AskMap.map)
    root.loadPreviousAskMap(PreviousAskMap.map) // Load previous run's askmap
    emit(s"[dse] Structure for app Lab2Part6GEMM")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app Lab2Part6GEMM")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part6GEMM///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App Lab2Part6GEMM: ${root.totalCycles()}")
    }
    end()
  }
}

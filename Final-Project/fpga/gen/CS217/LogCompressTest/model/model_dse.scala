package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x40 = new ControllerModel(40, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x40", "18", "Accel {", "x40 = AccelScope(Block(Const(())))"))
    val x117_ctrlast = CtrModel(0, 2, 1, 1)
    val x117 = new ControllerModel(117, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x117_ctrlast))), 0, 1, Ctx("x117", "22", "buf load inDRAM", "x117 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x10 = CtrModel(0, 2, 1, 1)
    val x11 = CChainModel(List[CtrModel[_,_,_,_]](x10), Ctx("x11", "24", "val mx = Reduce(Reg[T])(N by 1){ i => buf(i) }{ (a,b) => max(a,b) }", "List(x10 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x19 = new ControllerModel(19, InnerControl, Left(Pipelined), x11, 5, 1, Ctx("x19", "24", "val mx = Reduce(Reg[T])(N by 1){ i => buf(i) }{ (a,b) => max(a,b) }", "x19 = OpReduce(Set(),x11,x9,Block(x15),Block((x9) => x16),Block((b12,b13) => x17),Block((x9,x17) => x18),None,None,List(b14),None,Fix[TRUE,_16,_8])"))
    val x20 = CtrModel(0, 2, 1, 1)
    val x22 = CChainModel(List[CtrModel[_,_,_,_]](x20), Ctx("x22", "26", "Foreach(N by 1){ i =>", "List(x20 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x28 = new ControllerModel(28, InnerControl, Left(Pipelined), x22, 7, 7, Ctx("x28", "26", "Foreach(N by 1){ i =>", "x28 = OpForeach(Set(),x22,Block(Const(())),List(b21),None)"))
    val x29 = CtrModel(0, 2, 1, 1)
    val x31 = CChainModel(List[CtrModel[_,_,_,_]](x29), Ctx("x31", "30", "Foreach(N by 1){ i =>", "List(x29 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x38 = new ControllerModel(38, InnerControl, Left(Pipelined), x31, 3, 1, Ctx("x38", "30", "Foreach(N by 1){ i =>", "x38 = OpForeach(Set(),x31,Block(Const(())),List(b30),None)"))
    val x79 = new ControllerModel(79, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x79", "32", "if (i == 0) {", "x79 = SwitchCase(Block(x35))"))
    val x80 = new ControllerModel(80, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x80", "32", "if (i == 0) {", "x80 = SwitchCase(Block(x36))"))
    val x82 = new ControllerModel(82, OuterControl, Left(Fork), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x82", "32", "if (i == 0) {", "x82 = Switch(List(x34, x78),Block(x80))"))
    x82.registerChild(x79)
    x82.registerChild(x80)
    x38.registerChild(x82)
    val x149_ctrlast = CtrModel(0, 2, 1, 1)
    val x149 = new ControllerModel(149, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x149_ctrlast))), 0, 1, Ctx("x149", "39", "outDRAM store out", "x149 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x40.registerChild(x117)
    x40.registerChild(x19)
    x40.registerChild(x28)
    x40.registerChild(x38)
    x40.registerChild(x149)
    return x40
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/LogCompressTest///results_dse")
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
    emit(s"[dse] Structure for app LogCompressTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app LogCompressTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/LogCompressTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App LogCompressTest: ${root.totalCycles()}")
    }
    end()
  }
}

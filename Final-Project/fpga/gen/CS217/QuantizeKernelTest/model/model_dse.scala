package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x26 = new ControllerModel(26, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x26", "21", "Accel {", "x26 = AccelScope(Block(Const(())))"))
    val x96_ctrlast = CtrModel(0, 9, 1, 1)
    val x96 = new ControllerModel(96, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x96_ctrlast))), 0, 1, Ctx("x96", "25", "inSRAM load inDRAM", "x96 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x8 = CtrModel(0, 9, 1, 1)
    val x10 = CChainModel(List[CtrModel[_,_,_,_]](x8), Ctx("x10", "27", "Foreach(N by 1) { i =>", "List(x8 = CounterNew(Const(0),Const(9),Const(1),Const(1)))"))
    val x24 = new ControllerModel(24, InnerControl, Left(Pipelined), x10, 14, 1, Ctx("x24", "27", "Foreach(N by 1) { i =>", "x24 = OpForeach(Set(),x10,Block(Const(())),List(b9),None)"))
    val x128_ctrlast = CtrModel(0, 9, 1, 1)
    val x128 = new ControllerModel(128, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x128_ctrlast))), 0, 1, Ctx("x128", "36", "outDRAM store outSRAM", "x128 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x26.registerChild(x96)
    x26.registerChild(x24)
    x26.registerChild(x128)
    return x26
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/QuantizeKernelTest///results_dse")
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
    emit(s"[dse] Structure for app QuantizeKernelTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app QuantizeKernelTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/QuantizeKernelTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App QuantizeKernelTest: ${root.totalCycles()}")
    }
    end()
  }
}

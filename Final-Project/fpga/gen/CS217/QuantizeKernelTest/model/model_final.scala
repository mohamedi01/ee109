package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x213 = new ControllerModel(213, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x213", "21", "Accel {", "x213 = AccelScope(Block(Const(())))"))
    val x289_ctrlast = CtrModel(0, 9, 1, 1)
    val x289 = new ControllerModel(289, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x289_ctrlast))), 0, 1, Ctx("x289", "25", "inSRAM load inDRAM", "x289 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x290 = CtrModel(0, 9, 1, 1)
    val x291 = CChainModel(List[CtrModel[_,_,_,_]](x290), Ctx("x291", "27", "Foreach(N by 1) { i =>", "List(x290 = CounterNew(Const(0),Const(9),Const(1),Const(1)))"))
    val x308 = new ControllerModel(308, InnerControl, Left(Pipelined), x291, 7, 1, Ctx("x308", "27", "Foreach(N by 1) { i =>", "x308 = UnrolledForeach(Set(),x291,Block(Const(())),List(List(b292)),List(List(b293)),None)"))
    val x341_ctrlast = CtrModel(0, 9, 1, 1)
    val x341 = new ControllerModel(341, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x341_ctrlast))), 0, 1, Ctx("x341", "36", "outDRAM store outSRAM", "x341 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x213.registerChild(x289)
    x213.registerChild(x308)
    x213.registerChild(x341)
    return x213
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/QuantizeKernelTest///results_final")
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
    emit(s"[final] Structure for app QuantizeKernelTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app QuantizeKernelTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/QuantizeKernelTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App QuantizeKernelTest: ${root.totalCycles()}")
    }
    end()
  }
}

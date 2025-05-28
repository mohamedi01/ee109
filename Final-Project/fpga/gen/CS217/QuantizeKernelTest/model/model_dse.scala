package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x32 = new ControllerModel(32, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x32", "27", "Accel {", "x32 = AccelScope(Block(Const(())))"))
    val x124_ctrlast = CtrModel(0, 9, 1, 1)
    val x124 = new ControllerModel(124, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x124_ctrlast))), 0, 1, Ctx("x124", "30", "inSRAM load inDRAM", "x124 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x12 = CtrModel(0, 9, 1, 1)
    val x14 = CChainModel(List[CtrModel[_,_,_,_]](x12), Ctx("x14", "31", "Foreach(N by 1) { i =>", "List(x12 = CounterNew(Const(0),Const(9),Const(1),Const(1)))"))
    val x30 = new ControllerModel(30, InnerControl, Left(Pipelined), x14, 67, 1, Ctx("x30", "31", "Foreach(N by 1) { i =>", "x30 = OpForeach(Set(),x14,Block(Const(())),List(b13),None)"))
    val x156_ctrlast = CtrModel(0, 9, 1, 1)
    val x156 = new ControllerModel(156, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x156_ctrlast))), 0, 1, Ctx("x156", "37", "outDRAM store outSRAM", "x156 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x32.registerChild(x124)
    x32.registerChild(x30)
    x32.registerChild(x156)
    return x32
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

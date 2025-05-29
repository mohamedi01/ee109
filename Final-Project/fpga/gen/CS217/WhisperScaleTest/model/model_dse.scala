package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x17 = new ControllerModel(17, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x17", "15", "Accel {", "x17 = AccelScope(Block(Const(())))"))
    val x93_ctrlast = CtrModel(0, 4, 1, 1)
    val x93 = new ControllerModel(93, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x93_ctrlast))), 0, 1, Ctx("x93", "17", "buf load inDRAM", "x93 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x8 = CtrModel(0, 4, 1, 1)
    val x10 = CChainModel(List[CtrModel[_,_,_,_]](x8), Ctx("x10", "18", "Foreach(N by 1) { i =>", "List(x8 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x15 = new ControllerModel(15, InnerControl, Left(Pipelined), x10, 7, 7, Ctx("x15", "18", "Foreach(N by 1) { i =>", "x15 = OpForeach(Set(),x10,Block(Const(())),List(b9),None)"))
    val x125_ctrlast = CtrModel(0, 4, 1, 1)
    val x125 = new ControllerModel(125, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x125_ctrlast))), 0, 1, Ctx("x125", "21", "outDRAM store buf", "x125 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x17.registerChild(x93)
    x17.registerChild(x15)
    x17.registerChild(x125)
    return x17
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/WhisperScaleTest///results_dse")
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
    emit(s"[dse] Structure for app WhisperScaleTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app WhisperScaleTest")
        root.printResults()
        root.storeAskMap("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/WhisperScaleTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App WhisperScaleTest: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x201 = new ControllerModel(201, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x201", "15", "Accel {", "x201 = AccelScope(Block(Const(())))"))
    val x288_ctrlast = CtrModel(0, 4, 1, 1)
    val x288 = new ControllerModel(288, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x288_ctrlast))), 0, 1, Ctx("x288", "17", "buf load inDRAM", "x288 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x289 = CtrModel(0, 4, 1, 1)
    val x290 = CChainModel(List[CtrModel[_,_,_,_]](x289), Ctx("x290", "18", "Foreach(N by 1) { i =>", "List(x289 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x298 = new ControllerModel(298, InnerControl, Left(Pipelined), x290, 3, 3, Ctx("x298", "18", "Foreach(N by 1) { i =>", "x298 = UnrolledForeach(Set(),x290,Block(Const(())),List(List(b291)),List(List(b292)),None)"))
    val x331_ctrlast = CtrModel(0, 4, 1, 1)
    val x331 = new ControllerModel(331, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x331_ctrlast))), 0, 1, Ctx("x331", "21", "outDRAM store buf", "x331 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x201.registerChild(x288)
    x201.registerChild(x298)
    x201.registerChild(x331)
    return x201
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/WhisperScaleTest///results_final")
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
    emit(s"[final] Structure for app WhisperScaleTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app WhisperScaleTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/WhisperScaleTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App WhisperScaleTest: ${root.totalCycles()}")
    }
    end()
  }
}

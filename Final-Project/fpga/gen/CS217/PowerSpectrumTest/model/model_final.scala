package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x481 = new ControllerModel(481, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x481", "20", "Accel {", "x481 = AccelScope(Block(Const(())))"))
    val x480 = new ControllerModel(480, OuterControl, Left(ForkJoin), CChainModel(Seq()), 0, 0, Ctx("x480", "82", "stage(ParallelPipe(scala.collection.immutable.Set[Bit](), stageBlock(bundledStms.foreach(visit))))", "x480 = ParallelPipe(Set(),Block(Const(())))"))
    val x363_ctrlast = CtrModel(0, 4, 1, 1)
    val x363 = new ControllerModel(363, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x363_ctrlast))), 0, 1, Ctx("x363", "25", "realSRAM load realDRAM", "x363 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x398_ctrlast = CtrModel(0, 4, 1, 1)
    val x398 = new ControllerModel(398, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x398_ctrlast))), 0, 1, Ctx("x398", "26", "imagSRAM load imagDRAM", "x398 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x480.registerChild(x363)
    x480.registerChild(x398)
    val x399 = CtrModel(0, 4, 1, 1)
    val x400 = CChainModel(List[CtrModel[_,_,_,_]](x399), Ctx("x400", "28", "Foreach(N by 1) { i =>", "List(x399 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x411 = new ControllerModel(411, InnerControl, Left(Pipelined), x400, 30, 1, Ctx("x411", "28", "Foreach(N by 1) { i =>", "x411 = UnrolledForeach(Set(),x400,Block(Const(())),List(List(b401)),List(List(b402)),None)"))
    val x444_ctrlast = CtrModel(0, 4, 1, 1)
    val x444 = new ControllerModel(444, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x444_ctrlast))), 0, 1, Ctx("x444", "32", "outDRAM store outSRAM", "x444 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x481.registerChild(x480)
    x481.registerChild(x411)
    x481.registerChild(x444)
    return x481
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/PowerSpectrumTest///results_final")
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
    emit(s"[final] Structure for app PowerSpectrumTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app PowerSpectrumTest")
        root.printResults()
        root.storeAskMap("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/PowerSpectrumTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App PowerSpectrumTest: ${root.totalCycles()}")
    }
    end()
  }
}

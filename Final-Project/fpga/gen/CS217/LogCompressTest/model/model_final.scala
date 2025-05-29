package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x439 = new ControllerModel(439, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x439", "18", "Accel {", "x439 = AccelScope(Block(Const(())))"))
    val x330_ctrlast = CtrModel(0, 2, 1, 1)
    val x330 = new ControllerModel(330, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x330_ctrlast))), 0, 1, Ctx("x330", "22", "buf load inDRAM", "x330 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x332 = CtrModel(0, 2, 1, 1)
    val x333 = CChainModel(List[CtrModel[_,_,_,_]](x332), Ctx("x333", "24", "val mx = Reduce(Reg[T])(N by 1){ i => buf(i) }{ (a,b) => max(a,b) }", "List(x332 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x343 = new ControllerModel(343, InnerControl, Left(Pipelined), x333, 4, 1, Ctx("x343", "24", "val mx = Reduce(Reg[T])(N by 1){ i => buf(i) }{ (a,b) => max(a,b) }", "x343 = UnrolledReduce(Set(),x333,Block((x331) => Const(())),List(List(b334)),List(List(b335)),None)"))
    val x344 = CtrModel(0, 2, 1, 1)
    val x345 = CChainModel(List[CtrModel[_,_,_,_]](x344), Ctx("x345", "26", "Foreach(N by 1){ i =>", "List(x344 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x355 = CtrModel(0, 2, 1, 1)
    val x356 = CChainModel(List[CtrModel[_,_,_,_]](x355), Ctx("x356", "30", "Foreach(N by 1){ i =>", "List(x355 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x438 = new ControllerModel(438, OuterControl, Left(ForkJoin), CChainModel(Seq()), 0, 0, Ctx("x438", "82", "stage(ParallelPipe(scala.collection.immutable.Set[Bit](), stageBlock(bundledStms.foreach(visit))))", "x438 = ParallelPipe(Set(),Block(Const(())))"))
    val x354 = new ControllerModel(354, InnerControl, Left(Pipelined), x345, 3, 3, Ctx("x354", "26", "Foreach(N by 1){ i =>", "x354 = UnrolledForeach(Set(),x345,Block(Const(())),List(List(b346)),List(List(b347)),None)"))
    val x366 = new ControllerModel(366, InnerControl, Left(Pipelined), x356, 2, 1, Ctx("x366", "30", "Foreach(N by 1){ i =>", "x366 = UnrolledForeach(Set(),x356,Block(Const(())),List(List(b357)),List(List(b358)),None)"))
    x438.registerChild(x354)
    x438.registerChild(x366)
    val x399_ctrlast = CtrModel(0, 2, 1, 1)
    val x399 = new ControllerModel(399, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x399_ctrlast))), 0, 1, Ctx("x399", "39", "outDRAM store out", "x399 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x439.registerChild(x330)
    x439.registerChild(x343)
    x439.registerChild(x438)
    x439.registerChild(x399)
    return x439
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/LogCompressTest///results_final")
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
    emit(s"[final] Structure for app LogCompressTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app LogCompressTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/LogCompressTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App LogCompressTest: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x614 = new ControllerModel(614, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x614", "29", "Accel {", "x614 = AccelScope(Block(Const(())))"))
    val x613 = new ControllerModel(613, OuterControl, Left(ForkJoin), CChainModel(Seq()), 0, 0, Ctx("x613", "82", "stage(ParallelPipe(scala.collection.immutable.Set[Bit](), stageBlock(bundledStms.foreach(visit))))", "x613 = ParallelPipe(Set(),Block(Const(())))"))
    val x476_ctrlast = CtrModel(0, 3, 1, 1)
    val x476 = new ControllerModel(476, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x476_ctrlast))), 0, 1, Ctx("x476", "34", "matSRAM load matDRAM", "x476 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    val x511_ctrlast = CtrModel(0, 3, 1, 1)
    val x511 = new ControllerModel(511, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x511_ctrlast))), 0, 1, Ctx("x511", "35", "vecSRAM load vecDRAM", "x511 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x613.registerChild(x476)
    x613.registerChild(x511)
    val x512 = CtrModel(0, 2, 1, 1)
    val x513 = CChainModel(List[CtrModel[_,_,_,_]](x512), Ctx("x513", "37", "Foreach(BANDS by 1) { i =>", "List(x512 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x541 = new ControllerModel(541, OuterControl, Left(Pipelined), x513, 0, 1, Ctx("x541", "37", "Foreach(BANDS by 1) { i =>", "x541 = UnrolledForeach(Set(),x513,Block(Const(())),List(List(b514)),List(List(b515)),None)"))
    val x518 = CtrModel(0, 3, 1, 1)
    val x519 = CChainModel(List[CtrModel[_,_,_,_]](x518), Ctx("x519", "40", "}{_ + _}", "List(x518 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x537 = new ControllerModel(537, InnerControl, Left(Pipelined), x519, 13, 1, Ctx("x537", "40", "}{_ + _}", "x537 = UnrolledReduce(Set(b515),x519,Block((x516) => Const(())),List(List(b520)),List(List(b521)),None)"))
    val x540 = new ControllerModel(540, InnerControl, Left(Sequenced), CChainModel(Seq()), 1, 1, Ctx("x540", "189", "Pipe {", "x540 = UnitPipe(Set(b515),Block(Const(())),None)"))
    x541.registerChild(x537)
    x541.registerChild(x540)
    val x574_ctrlast = CtrModel(0, 2, 1, 1)
    val x574 = new ControllerModel(574, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x574_ctrlast))), 0, 1, Ctx("x574", "44", "outDRAM store outSRAM", "x574 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 24.toDouble)
    x614.registerChild(x613)
    x614.registerChild(x541)
    x614.registerChild(x574)
    return x614
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/MelFilterbankTest///results_final")
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
    emit(s"[final] Structure for app MelFilterbankTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app MelFilterbankTest")
        root.printResults()
        root.storeAskMap("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/MelFilterbankTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App MelFilterbankTest: ${root.totalCycles()}")
    }
    end()
  }
}

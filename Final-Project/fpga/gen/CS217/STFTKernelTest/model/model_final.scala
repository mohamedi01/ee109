package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x710 = new ControllerModel(710, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x710", "19", "Accel {", "x710 = AccelScope(Block(Const(())))"))
    val x506 = CtrModel(0, 4, 1, 1)
    val x507 = CChainModel(List[CtrModel[_,_,_,_]](x506), Ctx("x507", "29", "Foreach(NFFT by 1) { i => winSRAM(i) = 1.to[T] }", "List(x506 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x708 = new ControllerModel(708, OuterControl, Left(ForkJoin), CChainModel(Seq()), 0, 0, Ctx("x708", "82", "stage(ParallelPipe(scala.collection.immutable.Set[Bit](), stageBlock(bundledStms.foreach(visit))))", "x708 = ParallelPipe(Set(),Block(Const(())))"))
    val x505_ctrlast = CtrModel(0, 6, 1, 1)
    val x505 = new ControllerModel(505, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x505_ctrlast))), 0, 1, Ctx("x505", "28", "inSRAM load inDRAM", "x505 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x511 = new ControllerModel(511, InnerControl, Left(Pipelined), x507, 1, 1, Ctx("x511", "29", "Foreach(NFFT by 1) { i => winSRAM(i) = 1.to[T] }", "x511 = UnrolledForeach(Set(),x507,Block(Const(())),List(List(b508)),List(List(b509)),None)"))
    x708.registerChild(x505)
    x708.registerChild(x511)
    val x512 = CtrModel(0, 2, 1, 1)
    val x513 = CChainModel(List[CtrModel[_,_,_,_]](x512), Ctx("x513", "31", "Foreach(FRAMES by 1) { f =>", "List(x512 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x552 = new ControllerModel(552, OuterControl, Left(Pipelined), x513, 0, 1, Ctx("x552", "31", "Foreach(FRAMES by 1) { f =>", "x552 = UnrolledForeach(Set(),x513,Block(Const(())),List(List(b514)),List(List(b515)),None)"))
    val x516 = CtrModel(0, 4, 1, 1)
    val x517 = CChainModel(List[CtrModel[_,_,_,_]](x516), Ctx("x517", "32", "Foreach(NFFT by 1) { i =>", "List(x516 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x528 = new ControllerModel(528, InnerControl, Left(Pipelined), x517, 12, 1, Ctx("x528", "32", "Foreach(NFFT by 1) { i =>", "x528 = UnrolledForeach(Set(b515),x517,Block(Const(())),List(List(b518)),List(List(b519)),None)"))
    val x529 = CtrModel(0, 3, 1, 1)
    val x530 = CChainModel(List[CtrModel[_,_,_,_]](x529), Ctx("x530", "36", "Foreach(NFFT/2 + 1 by 1) { k =>", "List(x529 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x537 = new ControllerModel(537, InnerControl, Left(Pipelined), x530, 3, 1, Ctx("x537", "36", "Foreach(NFFT/2 + 1 by 1) { k =>", "x537 = UnrolledForeach(Set(b515),x530,Block(Const(())),List(List(b531)),List(List(b532)),None)"))
    val x538 = CtrModel(0, 3, 1, 1)
    val x539 = CChainModel(List[CtrModel[_,_,_,_]](x538), Ctx("x539", "41", "Foreach(NFFT/2 + 1 by 1) { i =>", "List(x538 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x551 = new ControllerModel(551, InnerControl, Left(Pipelined), x539, 3, 1, Ctx("x551", "41", "Foreach(NFFT/2 + 1 by 1) { i =>", "x551 = UnrolledForeach(Set(b515),x539,Block(Const(())),List(List(b540)),List(List(b541)),None)"))
    x552.registerChild(x528)
    x552.registerChild(x537)
    x552.registerChild(x551)
    val x709 = new ControllerModel(709, OuterControl, Left(ForkJoin), CChainModel(Seq()), 0, 0, Ctx("x709", "82", "stage(ParallelPipe(scala.collection.immutable.Set[Bit](), stageBlock(bundledStms.foreach(visit))))", "x709 = ParallelPipe(Set(),Block(Const(())))"))
    val x608_ctrlast = CtrModel(0, 2, 1, 1)
    val x608 = new ControllerModel(608, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x608_ctrlast))), 0, 1, Ctx("x608", "47", "realDRAM store real2D", "x608 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x664_ctrlast = CtrModel(0, 2, 1, 1)
    val x664 = new ControllerModel(664, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x664_ctrlast))), 0, 1, Ctx("x664", "48", "imagDRAM store imag2D", "x664 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x709.registerChild(x608)
    x709.registerChild(x664)
    x710.registerChild(x708)
    x710.registerChild(x552)
    x710.registerChild(x709)
    return x710
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/STFTKernelTest///results_final")
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
    emit(s"[final] Structure for app STFTKernelTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app STFTKernelTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/STFTKernelTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App STFTKernelTest: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x50 = new ControllerModel(50, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x50", "19", "Accel {", "x50 = AccelScope(Block(Const(())))"))
    val x125_ctrlast = CtrModel(0, 6, 1, 1)
    val x125 = new ControllerModel(125, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x125_ctrlast))), 0, 1, Ctx("x125", "28", "inSRAM load inDRAM", "x125 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x14 = CtrModel(0, 4, 1, 1)
    val x16 = CChainModel(List[CtrModel[_,_,_,_]](x14), Ctx("x16", "29", "Foreach(NFFT by 1) { i => winSRAM(i) = 1.to[T] }", "List(x14 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x18 = new ControllerModel(18, InnerControl, Left(Pipelined), x16, 3, 1, Ctx("x18", "29", "Foreach(NFFT by 1) { i => winSRAM(i) = 1.to[T] }", "x18 = OpForeach(Set(),x16,Block(Const(())),List(b15),None)"))
    val x19 = CtrModel(0, 2, 1, 1)
    val x21 = CChainModel(List[CtrModel[_,_,_,_]](x19), Ctx("x21", "31", "Foreach(FRAMES by 1) { f =>", "List(x19 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x47 = new ControllerModel(47, OuterControl, Left(Pipelined), x21, 0, 1, Ctx("x47", "31", "Foreach(FRAMES by 1) { f =>", "x47 = OpForeach(Set(),x21,Block(Const(())),List(b20),None)"))
    val x22 = CtrModel(0, 4, 1, 1)
    val x24 = CChainModel(List[CtrModel[_,_,_,_]](x22), Ctx("x24", "32", "Foreach(NFFT by 1) { i =>", "List(x22 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x31 = new ControllerModel(31, InnerControl, Left(Pipelined), x24, 16, 1, Ctx("x31", "32", "Foreach(NFFT by 1) { i =>", "x31 = OpForeach(Set(),x24,Block(Const(())),List(b23),None)"))
    val x32 = CtrModel(0, 3, 1, 1)
    val x34 = CChainModel(List[CtrModel[_,_,_,_]](x32), Ctx("x34", "36", "Foreach(NFFT/2 + 1 by 1) { k =>", "List(x32 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x38 = new ControllerModel(38, InnerControl, Left(Pipelined), x34, 7, 1, Ctx("x38", "36", "Foreach(NFFT/2 + 1 by 1) { k =>", "x38 = OpForeach(Set(),x34,Block(Const(())),List(b33),None)"))
    val x39 = CtrModel(0, 3, 1, 1)
    val x41 = CChainModel(List[CtrModel[_,_,_,_]](x39), Ctx("x41", "41", "Foreach(NFFT/2 + 1 by 1) { i =>", "List(x39 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x46 = new ControllerModel(46, InnerControl, Left(Pipelined), x41, 7, 1, Ctx("x46", "41", "Foreach(NFFT/2 + 1 by 1) { i =>", "x46 = OpForeach(Set(),x41,Block(Const(())),List(b40),None)"))
    x47.registerChild(x31)
    x47.registerChild(x38)
    x47.registerChild(x46)
    val x127 = CtrModel(0, 3, 1, 1)
    val x129 = CChainModel(List[CtrModel[_,_,_,_]](x127), Ctx("x129", "47", "realDRAM store real2D", "List(x127 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x180_ctrlast = CtrModel(0, 2, 1, 1)
    val x180 = new ControllerModel(180, OuterControl, Left(GatedDenseStore), List(x129, CChainModel(List(x180_ctrlast))), 0, 1, Ctx("x180", "47", "realDRAM store real2D", "x180 = OpForeach(Set(),x129,Block(Const(())),List(b128),None)"), bitsPerCycle = 32.toDouble)
    val x181 = CtrModel(0, 3, 1, 1)
    val x183 = CChainModel(List[CtrModel[_,_,_,_]](x181), Ctx("x183", "48", "imagDRAM store imag2D", "List(x181 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x234_ctrlast = CtrModel(0, 2, 1, 1)
    val x234 = new ControllerModel(234, OuterControl, Left(GatedDenseStore), List(x183, CChainModel(List(x234_ctrlast))), 0, 1, Ctx("x234", "48", "imagDRAM store imag2D", "x234 = OpForeach(Set(),x183,Block(Const(())),List(b182),None)"), bitsPerCycle = 32.toDouble)
    x50.registerChild(x125)
    x50.registerChild(x18)
    x50.registerChild(x47)
    x50.registerChild(x180)
    x50.registerChild(x234)
    return x50
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/STFTKernelTest///results_dse")
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
    emit(s"[dse] Structure for app STFTKernelTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app STFTKernelTest")
        root.printResults()
        root.storeAskMap("/Users/williambriger/Documents/Stanford/Junior Year /Spring Quarter/EE 109/ee109-1/Final-Project/fpga/./gen/CS217/STFTKernelTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App STFTKernelTest: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x244 = new ControllerModel(244, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x244", "31", "Accel {", "x244 = AccelScope(Block(Const(())))"))
    val x316_ctrlast = CtrModel(0, 9, 1, 1)
    val x316 = new ControllerModel(316, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x316_ctrlast))), 0, 1, Ctx("x316", "35", "inSRAM load inDRAM", "x316 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x317 = CtrModel(0, 9, 1, 1)
    val x318 = CChainModel(List[CtrModel[_,_,_,_]](x317), Ctx("x318", "37", "Foreach(N by 1) { i =>", "List(x317 = CounterNew(Const(0),Const(9),Const(1),Const(1)))"))
    val x350 = new ControllerModel(350, InnerControl, Left(Pipelined), x318, 63, 1, Ctx("x350", "37", "Foreach(N by 1) { i =>", "x350 = UnrolledForeach(Set(),x318,Block(Const(())),List(List(b319)),List(List(b320)),None)"))
    val x383_ctrlast = CtrModel(0, 9, 1, 1)
    val x383 = new ControllerModel(383, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x383_ctrlast))), 0, 1, Ctx("x383", "54", "outDRAM store outSRAM", "x383 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x244.registerChild(x316)
    x244.registerChild(x350)
    x244.registerChild(x383)
    return x244
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

package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x25 = new ControllerModel(25, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x25", "20", "Accel {", "x25 = AccelScope(Block(Const(())))"))
    val x98_ctrlast = CtrModel(0, 4, 1, 1)
    val x98 = new ControllerModel(98, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x98_ctrlast))), 0, 1, Ctx("x98", "25", "realSRAM load realDRAM", "x98 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x133_ctrlast = CtrModel(0, 4, 1, 1)
    val x133 = new ControllerModel(133, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x133_ctrlast))), 0, 1, Ctx("x133", "26", "imagSRAM load imagDRAM", "x133 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x14 = CtrModel(0, 4, 1, 1)
    val x16 = CChainModel(List[CtrModel[_,_,_,_]](x14), Ctx("x16", "28", "Foreach(N by 1) { i =>", "List(x14 = CounterNew(Const(0),Const(4),Const(1),Const(1)))"))
    val x23 = new ControllerModel(23, InnerControl, Left(Pipelined), x16, 27, 1, Ctx("x23", "28", "Foreach(N by 1) { i =>", "x23 = OpForeach(Set(),x16,Block(Const(())),List(b15),None)"))
    val x165_ctrlast = CtrModel(0, 4, 1, 1)
    val x165 = new ControllerModel(165, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x165_ctrlast))), 0, 1, Ctx("x165", "32", "outDRAM store outSRAM", "x165 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x25.registerChild(x98)
    x25.registerChild(x133)
    x25.registerChild(x23)
    x25.registerChild(x165)
    return x25
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/PowerSpectrumTest///results_dse")
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
    emit(s"[dse] Structure for app PowerSpectrumTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app PowerSpectrumTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/PowerSpectrumTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App PowerSpectrumTest: ${root.totalCycles()}")
    }
    end()
  }
}

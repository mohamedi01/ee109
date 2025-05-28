package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x48 = new ControllerModel(48, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x48", "29", "Accel {", "x48 = AccelScope(Block(Const(())))"))
    val x74 = CtrModel(0, 2, 1, 1)
    val x76 = CChainModel(List[CtrModel[_,_,_,_]](x74), Ctx("x76", "34", "matSRAM load matDRAM", "List(x74 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x130_ctrlast = CtrModel(0, 3, 1, 1)
    val x130 = new ControllerModel(130, OuterControl, Left(DenseLoad), List(x76, CChainModel(List(x130_ctrlast))), 0, 1, Ctx("x130", "34", "matSRAM load matDRAM", "x130 = OpForeach(Set(),x76,Block(Const(())),List(b75),None)"), bitsPerCycle = 32.toDouble)
    val x165_ctrlast = CtrModel(0, 3, 1, 1)
    val x165 = new ControllerModel(165, OuterControl, Left(DenseLoad), List(CChainModel(Seq()), CChainModel(Seq(x165_ctrlast))), 0, 1, Ctx("x165", "35", "vecSRAM load vecDRAM", "x165 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    val x28 = CtrModel(0, 2, 1, 1)
    val x30 = CChainModel(List[CtrModel[_,_,_,_]](x28), Ctx("x30", "37", "Foreach(BANDS by 1) { i =>", "List(x28 = CounterNew(Const(0),Const(2),Const(1),Const(1)))"))
    val x46 = new ControllerModel(46, OuterControl, Left(Pipelined), x30, 0, 1, Ctx("x46", "37", "Foreach(BANDS by 1) { i =>", "x46 = OpForeach(Set(),x30,Block(Const(())),List(b29),None)"))
    val x32 = CtrModel(0, 3, 1, 1)
    val x33 = CChainModel(List[CtrModel[_,_,_,_]](x32), Ctx("x33", "40", "}{_ + _}", "List(x32 = CounterNew(Const(0),Const(3),Const(1),Const(1)))"))
    val x43 = new ControllerModel(43, InnerControl, Left(Pipelined), x33, 25, 1, Ctx("x43", "40", "}{_ + _}", "x43 = OpReduce(Set(),x33,x31,Block(x39),Block((x31) => x40),Block((b34,b35) => x41),Block((x31,x41) => x42),None,None,List(b36),None,Flt[_24,_8])"))
    x46.registerChild(x43)
    val x197_ctrlast = CtrModel(0, 2, 1, 1)
    val x197 = new ControllerModel(197, OuterControl, Left(GatedDenseStore), List(CChainModel(Seq()), CChainModel(Seq(x197_ctrlast))), 0, 1, Ctx("x197", "44", "outDRAM store outSRAM", "x197 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x48.registerChild(x130)
    x48.registerChild(x165)
    x48.registerChild(x46)
    x48.registerChild(x197)
    return x48
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/MelFilterbankTest///results_dse")
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
    emit(s"[dse] Structure for app MelFilterbankTest")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app MelFilterbankTest")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/Final-Project/fpga/./gen/CS217/MelFilterbankTest///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App MelFilterbankTest: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x23 = new ControllerModel(23, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x23", "9", "Accel {", "x23 = AccelScope(Block(Const(())))"))
    val x3 = CtrModel(-5, 5, 1, 1)
    val x4 = CChainModel(List[CtrModel[_,_,_,_]](x3), Ctx("x4", "15", "}{_+_}", "List(x3 = CounterNew(Const(-5),Const(5),Const(1),Const(1)))"))
    val x5 = CtrModel(0, 16, 1, 1)
    val x6 = CChainModel(List[CtrModel[_,_,_,_]](x5), Ctx("x6", "15", "}{_+_}", "Vector(x5 = CounterNew(Const(0),Const(16),Const(1),Const(1)))"))
    val x21 = new ControllerModel(21, OuterControl, Left(Pipelined), List(x4, x6), 0, 1, Ctx("x21", "15", "}{_+_}", "x21 = OpMemReduce(Set(),x4,x6,x2,Block(x11),Block((x11) => x18),Block((x2) => x19),Block((b9,b10) => x17),Block((x2,x17) => x20),None,false,List(b7),List(b8),None,Fix[TRUE,_32,_0],SRAM1[Fix[TRUE,_32,_0]])"))
    val x12 = CtrModel(0, 16, 1, 1)
    val x14 = CChainModel(List[CtrModel[_,_,_,_]](x12), Ctx("x14", "13", "Foreach(16 by 1) { j => tmp(j) = 1}", "List(x12 = CounterNew(Const(0),Const(16),Const(1),Const(1)))"))
    val x16 = new ControllerModel(16, InnerControl, Left(Pipelined), x14, 3, 1, Ctx("x16", "13", "Foreach(16 by 1) { j => tmp(j) = 1}", "x16 = OpForeach(Set(),x14,Block(Const(())),List(b13),None)"))
    x21.registerChild(x16)
    val x84_ctrlast = CtrModel(0, 16, 1, 1)
    val x84 = new ControllerModel(84, OuterControl, Left(DenseStore), List(CChainModel(Seq()), CChainModel(Seq(x84_ctrlast))), 0, 1, Ctx("x84", "16", "out store a", "x84 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x23.registerChild(x21)
    x23.registerChild(x84)
    return x23
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part1SimpleMemReduce///results_dse")
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
    emit(s"[dse] Structure for app Lab2Part1SimpleMemReduce")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app Lab2Part1SimpleMemReduce")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part1SimpleMemReduce///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App Lab2Part1SimpleMemReduce: ${root.totalCycles()}")
    }
    end()
  }
}

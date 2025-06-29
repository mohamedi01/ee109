package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x119 = new ControllerModel(119, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x119", "9", "Accel {", "x119 = AccelScope(Block(Const(())))"))
    val x162 = CtrModel(-5, 5, 1, 1)
    val x163 = CChainModel(List[CtrModel[_,_,_,_]](x162), Ctx("x163", "15", "}{_+_}", "List(x162 = CounterNew(Const(-5),Const(5),Const(1),Const(1)))"))
    val x164 = CtrModel(0, 16, 1, 1)
    val x165 = CChainModel(List[CtrModel[_,_,_,_]](x164), Ctx("x165", "15", "}{_+_}", "Vector(x164 = CounterNew(Const(0),Const(16),Const(1),Const(1)))"))
    val x187 = new ControllerModel(187, OuterControl, Left(Pipelined), x163, 0, 1, Ctx("x187", "15", "}{_+_}", "x187 = UnrolledReduce(Set(),x163,Block((x161) => Const(())),List(List(b166)),List(List(b168)),None)"))
    val x171 = CtrModel(0, 16, 1, 1)
    val x172 = CChainModel(List[CtrModel[_,_,_,_]](x171), Ctx("x172", "13", "Foreach(16 by 1) { j => tmp(j) = 1}", "List(x171 = CounterNew(Const(0),Const(16),Const(1),Const(1)))"))
    val x176 = new ControllerModel(176, InnerControl, Left(Pipelined), x172, 1, 1, Ctx("x176", "13", "Foreach(16 by 1) { j => tmp(j) = 1}", "x176 = UnrolledForeach(Set(b168),x172,Block(Const(())),List(List(b173)),List(List(b174)),None)"))
    val x186 = new ControllerModel(186, InnerControl, Left(Pipelined), x165, 4, 1, Ctx("x186", "15", "}{_+_}", "x186 = UnrolledForeach(Set(),x165,Block(Const(())),Vector(List(b167)),Vector(List(b169)),None)"))
    x187.registerChild(x176)
    x187.registerChild(x186)
    val x209_ctrlast = CtrModel(0, 16, 1, 1)
    val x209 = new ControllerModel(209, OuterControl, Left(DenseStore), List(CChainModel(Seq()), CChainModel(Seq(x209_ctrlast))), 0, 1, Ctx("x209", "16", "out store a", "x209 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x119.registerChild(x187)
    x119.registerChild(x209)
    return x119
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part1SimpleMemReduce///results_final")
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
    emit(s"[final] Structure for app Lab2Part1SimpleMemReduce")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app Lab2Part1SimpleMemReduce")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part1SimpleMemReduce///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App Lab2Part1SimpleMemReduce: ${root.totalCycles()}")
    }
    end()
  }
}

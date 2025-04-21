package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x168 = new ControllerModel(168, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x168", "9", "Accel {", "x168 = AccelScope(Block(Const(())))"))
    val x214 = new ControllerModel(214, InnerControl, Left(Sequenced), CChainModel(Seq()), 1, 1, Ctx("x214", "189", "Pipe {", "x214 = UnitPipe(Set(),Block(Const(())),None)"))
    val x245_fsm = CtrModel(0, Ask(245, "expected # iters for fsm", Ctx("x245_fsm", "24", "}{state => state + 1}", "x245 = StateMachine(Set(),Const(0),Block((b5) => x215),Block((b5) => Const(())),Block((b5) => x244),Fix[TRUE,_32,_0])")), 1, 1)
    val x245_cchain = CChainModel(List[CtrModel[_,_,_,_]](x245_fsm), Ctx("x245", "24", "}{state => state + 1}", "x245 = StateMachine(Set(),Const(0),Block((b5) => x215),Block((b5) => Const(())),Block((b5) => x244),Fix[TRUE,_32,_0])"))
    val x245 = new ControllerModel(245, InnerControl, Left(Sequenced), x245_cchain, 5 + 2, 2 + 2, Ctx("x245", "24", "}{state => state + 1}", "x245 = StateMachine(Set(),Const(0),Block((b5) => x215),Block((b5) => Const(())),Block((b5) => x244),Fix[TRUE,_32,_0])"))
    val x267_ctrlast = CtrModel(0, 32, 1, 1)
    val x267 = new ControllerModel(267, OuterControl, Left(DenseStore), List(CChainModel(Seq()), CChainModel(Seq(x267_ctrlast))), 0, 1, Ctx("x267", "26", "dram(0::32) store bram", "x267 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x168.registerChild(x214)
    x168.registerChild(x245)
    x168.registerChild(x267)
    return x168
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSM///results_final")
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
    emit(s"[final] Structure for app Lab2Part3BasicCondFSM")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app Lab2Part3BasicCondFSM")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSM///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App Lab2Part3BasicCondFSM: ${root.totalCycles()}")
    }
    end()
  }
}

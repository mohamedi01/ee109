package model
import models.Runtime._

object AppRuntimeModel_final extends App {
  def build_model(): ControllerModel = {
    val x164 = new ControllerModel(164, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x164", "44", "Accel {", "x164 = AccelScope(Block(Const(())))"))
    val x236_fsm = CtrModel(0, Ask(236, "expected # iters for fsm", Ctx("x236_fsm", "58", "}{state => state + 1}", "x236 = StateMachine(Set(),Const(0),Block((b5) => x208),Block((b5) => Const(())),Block((b5) => x235),Fix[TRUE,_32,_0])")), 1, 1)
    val x236_cchain = CChainModel(List[CtrModel[_,_,_,_]](x236_fsm), Ctx("x236", "58", "}{state => state + 1}", "x236 = StateMachine(Set(),Const(0),Block((b5) => x208),Block((b5) => Const(())),Block((b5) => x235),Fix[TRUE,_32,_0])"))
    val x236 = new ControllerModel(236, InnerControl, Left(Sequenced), x236_cchain, 5 + 2, 2 + 2, Ctx("x236", "58", "}{state => state + 1}", "x236 = StateMachine(Set(),Const(0),Block((b5) => x208),Block((b5) => Const(())),Block((b5) => x235),Fix[TRUE,_32,_0])"))
    val x258_ctrlast = CtrModel(0, 32, 1, 1)
    val x258 = new ControllerModel(258, OuterControl, Left(DenseStore), List(CChainModel(Seq()), CChainModel(Seq(x258_ctrlast))), 0, 1, Ctx("x258", "60", "dram(0::32) store bram", "x258 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x164.registerChild(x236)
    x164.registerChild(x258)
    return x164
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSMAlt///results_final")
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
    emit(s"[final] Structure for app Lab2Part3BasicCondFSMAlt")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[final] Runtime results for app Lab2Part3BasicCondFSMAlt")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSMAlt///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[final] Total Cycles for App Lab2Part3BasicCondFSMAlt: ${root.totalCycles()}")
    }
    end()
  }
}

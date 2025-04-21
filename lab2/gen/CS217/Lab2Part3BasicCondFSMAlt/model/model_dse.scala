package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x28 = new ControllerModel(28, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x28", "44", "Accel {", "x28 = AccelScope(Block(Const(())))"))
    val x25_fsm = CtrModel(0, Ask(25, "expected # iters for fsm", Ctx("x25_fsm", "58", "}{state => state + 1}", "x25 = StateMachine(Set(),Const(0),Block((b5) => x6),Block((b5) => x84),Block((b5) => x24),Fix[TRUE,_32,_0])")), 1, 1)
    val x25_cchain = CChainModel(List[CtrModel[_,_,_,_]](x25_fsm), Ctx("x25", "58", "}{state => state + 1}", "x25 = StateMachine(Set(),Const(0),Block((b5) => x6),Block((b5) => x84),Block((b5) => x24),Fix[TRUE,_32,_0])"))
    val x25 = new ControllerModel(25, InnerControl, Left(Sequenced), x25_cchain, 10 + 2, 2 + 2, Ctx("x25", "58", "}{state => state + 1}", "x25 = StateMachine(Set(),Const(0),Block((b5) => x6),Block((b5) => x84),Block((b5) => x24),Fix[TRUE,_32,_0])"))
    val x71 = new ControllerModel(71, InnerControl, Left(Sequenced), CChainModel(List()), 9 + 2, 1 + 2, Ctx("x71", "49", "if (state < 8) {", "x71 = SwitchCase(Block(x8))"))
    val x73 = new ControllerModel(73, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x73", "51", "} else if (state < 16 && state >= 8) {", "x73 = SwitchCase(Block(x13))"))
    val x75 = new ControllerModel(75, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x75", "53", "} else if (state < 24 && state >= 16) {", "x75 = SwitchCase(Block(x18))"))
    val x76 = new ControllerModel(76, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x76", "53", "} else if (state < 24 && state >= 16) {", "x76 = SwitchCase(Block(x20))"))
    val x82 = new ControllerModel(82, OuterControl, Left(Fork), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x82", "53", "} else if (state < 24 && state >= 16) {", "x82 = Switch(List(x16, x74),Block(x76))"))
    x82.registerChild(x75)
    x82.registerChild(x76)
    val x78 = new ControllerModel(78, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x78", "51", "} else if (state < 16 && state >= 8) {", "x78 = SwitchCase(Block(x82))"))
    x78.registerChild(x82)
    val x83 = new ControllerModel(83, OuterControl, Left(Fork), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x83", "51", "} else if (state < 16 && state >= 8) {", "x83 = Switch(List(x11, x72),Block(x78))"))
    x83.registerChild(x73)
    x83.registerChild(x78)
    val x80 = new ControllerModel(80, InnerControl, Left(Sequenced), CChainModel(List()), 9 + 2, 1 + 2, Ctx("x80", "49", "if (state < 8) {", "x80 = SwitchCase(Block(x83))"))
    x80.registerChild(x83)
    val x84 = new ControllerModel(84, OuterControl, Left(Fork), CChainModel(List()), 9 + 2, 1 + 2, Ctx("x84", "49", "if (state < 8) {", "x84 = Switch(List(x7, x70),Block(x80))"))
    x84.registerChild(x71)
    x84.registerChild(x80)
    x25.registerChild(x84)
    val x109_ctrlast = CtrModel(0, 32, 1, 1)
    val x109 = new ControllerModel(109, OuterControl, Left(DenseStore), List(CChainModel(Seq()), CChainModel(Seq(x109_ctrlast))), 0, 1, Ctx("x109", "60", "dram(0::32) store bram", "x109 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x28.registerChild(x25)
    x28.registerChild(x109)
    return x28
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSMAlt///results_dse")
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
    emit(s"[dse] Structure for app Lab2Part3BasicCondFSMAlt")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app Lab2Part3BasicCondFSMAlt")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSMAlt///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App Lab2Part3BasicCondFSMAlt: ${root.totalCycles()}")
    }
    end()
  }
}

package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x27 = new ControllerModel(27, OuterControl, Left(Sequenced), CChainModel(Seq()), 0, 1, Ctx("x27", "9", "Accel {", "x27 = AccelScope(Block(Const(())))"))
    val x24_fsm = CtrModel(0, Ask(24, "expected # iters for fsm", Ctx("x24_fsm", "24", "}{state => state + 1}", "x24 = StateMachine(Set(),Const(0),Block((b5) => x6),Block((b5) => x87),Block((b5) => x23),Fix[TRUE,_32,_0])")), 1, 1)
    val x24_cchain = CChainModel(List[CtrModel[_,_,_,_]](x24_fsm), Ctx("x24", "24", "}{state => state + 1}", "x24 = StateMachine(Set(),Const(0),Block((b5) => x6),Block((b5) => x87),Block((b5) => x23),Fix[TRUE,_32,_0])"))
    val x24 = new ControllerModel(24, InnerControl, Left(Sequenced), x24_cchain, 5 + 2, 2 + 2, Ctx("x24", "24", "}{state => state + 1}", "x24 = StateMachine(Set(),Const(0),Block((b5) => x6),Block((b5) => x87),Block((b5) => x23),Fix[TRUE,_32,_0])"))
    val x71 = new ControllerModel(71, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x71", "15", "if (state < 8) {", "x71 = SwitchCase(Block(x10))"))
    val x72 = new ControllerModel(72, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x72", "15", "if (state < 8) {", "x72 = SwitchCase(Block(x13))"))
    val x85 = new ControllerModel(85, OuterControl, Left(Fork), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x85", "15", "if (state < 8) {", "x85 = Switch(List(x8, x70),Block(x72))"))
    x85.registerChild(x71)
    x85.registerChild(x72)
    val x74 = new ControllerModel(74, InnerControl, Left(Sequenced), CChainModel(List()), 4 + 2, 1 + 2, Ctx("x74", "14", "if (state < 16) {", "x74 = SwitchCase(Block(x85))"))
    x74.registerChild(x85)
    val x79 = new ControllerModel(79, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x79", "22", "bram(state - 16) = if (state == 16) 17 else if (state == 17) reg.value else state // Test const, regread, and bound Mux1H", "x79 = SwitchCase(Block(Const(17)))"))
    val x80 = new ControllerModel(80, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x80", "22", "bram(state - 16) = if (state == 16) 17 else if (state == 17) reg.value else state // Test const, regread, and bound Mux1H", "x80 = SwitchCase(Block(x18))"))
    val x81 = new ControllerModel(81, InnerControl, Left(Sequenced), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x81", "22", "bram(state - 16) = if (state == 16) 17 else if (state == 17) reg.value else state // Test const, regread, and bound Mux1H", "x81 = SwitchCase(Block(b5))"))
    val x86 = new ControllerModel(86, OuterControl, Left(Fork), CChainModel(List()), 0 + 2, 1 + 2, Ctx("x86", "22", "bram(state - 16) = if (state == 16) 17 else if (state == 17) reg.value else state // Test const, regread, and bound Mux1H", "x86 = Switch(List(x16, x76, x78),Block(x81))"))
    x86.registerChild(x79)
    x86.registerChild(x80)
    x86.registerChild(x81)
    val x83 = new ControllerModel(83, InnerControl, Left(Sequenced), CChainModel(List()), 4 + 2, 1 + 2, Ctx("x83", "14", "if (state < 16) {", "x83 = SwitchCase(Block(x21))"))
    x83.registerChild(x86)
    val x87 = new ControllerModel(87, OuterControl, Left(Fork), CChainModel(List()), 4 + 2, 1 + 2, Ctx("x87", "14", "if (state < 16) {", "x87 = Switch(List(x7, x69),Block(x83))"))
    x87.registerChild(x74)
    x87.registerChild(x83)
    x24.registerChild(x87)
    val x108_ctrlast = CtrModel(0, 32, 1, 1)
    val x108 = new ControllerModel(108, OuterControl, Left(DenseStore), List(CChainModel(Seq()), CChainModel(Seq(x108_ctrlast))), 0, 1, Ctx("x108", "26", "dram(0::32) store bram", "x108 = UnitPipe(Set(),Block(Const(())),None)"), bitsPerCycle = 32.toDouble)
    x27.registerChild(x24)
    x27.registerChild(x108)
    return x27
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSM///results_dse")
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
    emit(s"[dse] Structure for app Lab2Part3BasicCondFSM")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app Lab2Part3BasicCondFSM")
        root.printResults()
        root.storeAskMap("/Users/mohamedismail/Desktop/EE109/ee109/lab2/./gen/CS217/Lab2Part3BasicCondFSM///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App Lab2Part3BasicCondFSM: ${root.totalCycles()}")
    }
    end()
  }
}

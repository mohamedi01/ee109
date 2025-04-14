package model
import models.Runtime._

object AppRuntimeModel_dse extends App {
  def build_model(): ControllerModel = {
    val x20 = new ControllerModel(20, InnerControl, Left(Sequenced), CChainModel(Seq()), 3, 1, Ctx("x20", "37", "Accel {", "x20 = AccelScope(Block(Const(())))"))
    return x20
  }
  
  override def main(args: Array[String]): Unit = {
    begin("/Users/williambriger/Documents/Stanford - General/Junior Year /Winter Quarter/CS 124/Hw #7/ee109/lab1/./gen/CS217/Lab1Part1RegExample///results_dse")
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
    emit(s"[dse] Structure for app Lab1Part1RegExample")
    allTuneParams.foreach{tuneTo => 
        tuneParams = tuneTo
        root.printStructure()
        root.execute()
        emit(s"[dse] Runtime results for app Lab1Part1RegExample")
        root.printResults()
        root.storeAskMap("/Users/williambriger/Documents/Stanford - General/Junior Year /Winter Quarter/CS 124/Hw #7/ee109/lab1/./gen/CS217/Lab1Part1RegExample///model/PreviousAskMap.scala") // Store this run's askmap
        emit(s"[dse] Total Cycles for App Lab1Part1RegExample: ${root.totalCycles()}")
    }
    end()
  }
}

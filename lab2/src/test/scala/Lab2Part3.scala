import spatial.dsl._


// FSM
@spatial class Lab2Part3BasicCondFSM extends SpatialTest {

  def main(args: Array[String]): Unit = {
    val dram = DRAM[Int](32)
    Accel {
      val bram = SRAM[Int](32)
      val reg = Reg[Int](0)
      reg := 16
      FSM(0)(state => state < 32) { state =>
        if (state < 16) {
          if (state < 8) {
            bram(31 - state) = state // 16:31 [7, 6, ... 0]
          } else {
            bram(31 - state) = state+1 // 16:31 [16, 15, ... 9]
          }
        }
        else {
          bram(state - 16) = if (state == 16) 17 else if (state == 17) reg.value else state // Test const, regread, and bound Mux1H
        }
      }{state => state + 1}

      dram(0::32) store bram
    }
    val result = getMem(dram)
    val gold = Array[Int](17, 16, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28,
      29, 30, 31, 16, 15, 14, 13, 12, 11, 10, 9, 7, 6, 5, 4, 3, 2, 1, 0)
    printArray(result, "Result")
    printArray(gold, "Gold")
    val cksum = gold.zip(result){_ == _}.reduce{_&&_}
    println("PASS: " + cksum + " (Lab2Part3BasicCondFSM)")
    assert(cksum == 1)
  }
}


@spatial class Lab2Part3BasicCondFSMAlt extends SpatialTest {

  def main(args: Array[String]): Unit = {
    val dram = DRAM[Int](32)
    Accel {
      val bram = SRAM[Int](32)
      val reg = Reg[Int](0)
      reg := 16
      FSM(0)(state => state < 32) { state =>
        // TODO: Fill in here
      }{// TODO: Fill in here}

      dram(0::32) store bram
    }
    val result = getMem(dram)
    val gold = Array[Int]( 0, 1, 2, 3, 4, 5, 6, 7,
    16, 18, 20, 22, 24, 26, 28, 30,
    48, 51, 54, 57, 60, 63, 66, 69,
    96,100,104,108,112,116,120,124)
    printArray(result, "Result")
    printArray(gold, "Gold")
    val cksum = gold.zip(result){_ == _}.reduce{_&&_}
    println("PASS: " + cksum + " (Lab2Part3BasicCondFSMAlt)")
    assert(cksum == 1)
  }
}
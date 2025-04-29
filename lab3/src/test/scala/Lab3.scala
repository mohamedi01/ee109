import spatial.dsl._


@spatial class Lab3Part1Convolution extends SpatialTest {
  
  // Set the size of the image
  // As the size of the kernel is 3x3, we will pad each dimension by 2.
  // You can assume (img_r + 2 * 2) and (img_c + 2 * 2) are multiples of 16. We will only test cases such as 12, 28, 44 ...
  val img_r = 12
  val img_c = 12

  // Filters size
  val Kh = 3
  val Kw = 3

  type T = Int

  def main(args: Array[String]): Unit = {
    val pad_r = Kh-1
    val pad_c = Kw-1

    // Prepare a properly padded input image
    val R = img_r + pad_r * 2
    val C = img_c + pad_c * 2
    val image = (0::R, 0::C){(i,j) => if (j >= pad_c && j < C-pad_c && i >= pad_r && i < R - pad_r) (i-pad_r+1)*16 else 0}

    val img = DRAM[T](R, C)
    val imgOut = DRAM[T](R, C)

    // val debugOut = DRAM[T](3*R, 3*C) // FOR DEBUGGING

    setMem(img, image)

    Accel {
      // Create line buffer, shift register, and SRAM to store the results
      val lb = LineBuffer[T](Kh, C)
      val sr = RegFile[T](Kh, Kw)
      val lineOut = SRAM[T](C)

      // val debugSRAM = SRAM[T](3, 3*C)  // FOR DEBUGGING

      // Filters (Rotated 180 degrees due to how data is loaded into the linebuffer)
      val kh = LUT[T](3,3)(
        -1.to[T], 0.to[T], 1.to[T],
        -2.to[T], 0.to[T], 2.to[T],
        -1.to[T], 0.to[T], 1.to[T])
      
      val kv = LUT[T](3,3)(
        -1.to[T], -2.to[T], -1.to[T],
         0.to[T],  0.to[T],  0.to[T],
         1.to[T],  2.to[T],  1.to[T])

      /* Filters (rotated 180 degrees)
         1   2   1      -1 -2 -1
         0   0   0  ->   0  0  0
        -1  -2  -1       1  2  1

         1   0  -1      -1  0  1
         2   0  -2  ->  -2  0  2
         1   0  -1      -1  0  1
      */


      Foreach(0 until R) { r =>
        lb load img(r, 0::C)

        Sequential.Foreach(0 until C) { c =>
          Pipe{sr.reset(c == 0)}
          // Shifting in data from the line buffer
          Foreach(0 until Kh par Kh){i => sr(i, *) <<= lb(i, c) }
          
          // Foreach(0 until Kh, 0 until Kw){(i,j) => debugSRAM(i,3*c+j) = sr(i,j)} // FOR DEBUGGING

          // Implement the computation part for a 2-D convolution.
          // Use horz and vert to store your convolution results.
          // Your code here:
          val horz = Reduce(Reg[T])(Kh by 1 par Kh, Kw by 1 par Kw){ (i,j) => sr(i,j) * kh(i,j)}{_ + _}
          val vert = Reduce(Reg[T])(Kh by 1 par Kh, Kw by 1 par Kw){ (i,j) => sr(i,j) * kv(i,j)}{_ + _}

          val mag = abs(horz.value) + abs(vert.value)
          lineOut(c) = mux( (r < pad_r || c < pad_c), 0.to[T], abs(horz.value) + abs(vert.value)) 
        }
        imgOut(r, 0::C par 16) store lineOut
        // debugOut(r*3::r*3+3, 0::3*R) store debugSRAM  // FOR DEBUGGING
      }
    }

    val result = getMatrix(imgOut)
    
    // val debugResult = getMatrix(debugOut) // FOR DEBUGGING

    // Calculate the gold
    val out_r = img_r + 2*pad_r - Kh + 1
    val out_c = img_c + 2*pad_c - Kw + 1

    val raw_gold = (0::out_r, 0::out_c){(i,j) =>
      val px00 = image(i,j)
      val px01 = image(i,j+1)
      val px02 = image(i,j+2)
      val px10 = image(i+1,j)
      val px11 = image(i+1,j+1)
      val px12 = image(i+1,j+2)
      val px20 = image(i+2,j)
      val px21 = image(i+2,j+1)
      val px22 = image(i+2,j+2)
      abs(px00 * 1 + px01 * 2 + px02 * 1 - px20 * 1 - px21 * 2 - px22 * 1) + abs(px00 * 1 - px02 * 1 + px10 * 2 - px12 * 2 + px20 * 1 - px22 * 1)
    }

    // Due to the behavior of linebuffers, the output from Accel will be padded (top: 2, bottom: 2)
    val gold = (0::R, 0::C){(i,j) =>
      if (i < pad_r || j < pad_c) {
        0
      } else { // as we pad the top by 2 and left by 3, gold[i,j] = raw_gold[i-2, j-3]
        raw_gold(i-pad_r,j-pad_c)
      } 
    }
    

    printMatrix(image, "Image")
    printMatrix(gold, "Gold")
    printMatrix(result, "Output")

    // printMatrix(debugResult, "Debug") // FOR DEBUGGING
    
    val cksum = gold.zip(result){_ == _}.reduce{_&&_}
    println("PASS: " + cksum + " (Lab3Part1Convolution)")
    assert(cksum == 1)
  }
}
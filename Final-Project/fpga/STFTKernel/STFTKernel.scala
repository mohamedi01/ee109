import spatial.dsl._

//=== STFTKernel.scala (skeleton) ===
@spatial object STFTKernel {
  def main(args: Array[String]): Unit = {
    val nfft   = args(0).to[I32]      // e.g., 400
    val hop    = args(1).to[I32]      // e.g., 160
    val frames = args(2).to[I32]
    val inDramR = DRAM[Float](nfft * frames)
    val realDram = DRAM[Float](nfft/2+1, frames)
    val imagDram = DRAM[Float](nfft/2+1, frames)
    Accel {
      val winSram = SRAM[Float](nfft)
      // Initialize Hann window
      PipeForeach(nfft by 1) { i => winSram(i) = hann(i.to[Float], nfft.to[Float]) }
      val frameBuf = SRAM[Float](nfft)
      val realSram = SRAM[Float](nfft/2+1)
      val imagSram = SRAM[Float](nfft/2+1)
      Foreach(frames by 1) { f =>
        frameBuf load inDramR.slice(f*hop :: f*hop + nfft)
        Foreach(nfft by 1) { i => frameBuf(i) = frameBuf(i) * winSram(i) }
        val (r, im) = FFT(frameBuf) // placeholder for Spatial FFT
        Foreach(nfft/2+1 by 1) { k =>
          realSram(k) = r(k)
          imagSram(k) = im(k)
        }
        realDram.store(realSram, 0::nfft/2+1, f)
        imagDram.store(imagSram, 0::nfft/2+1, f)
      }
    }
  }
}

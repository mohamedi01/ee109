import spatial.dsl._

//=== QuantizeKernel.scala ===
@spatial object QuantizeKernel {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val inDram = DRAM[Float](n)
    val outDram = DRAM[Float](n)
    Accel {
      val inSram  = SRAM[Float](n)
      val outSram = SRAM[Float](n)
      inSram load inDram
      Foreach(n by 1) { i =>
        val scaled  = inSram(i) * 32767.to[Float]
        val clipped = clamp(scaled, -32768.to[Float], 32767.to[Float])
        outSram(i) = clipped.to[Int].to[Float] / 32768.to[Float]
      }
      outDram store outSram
    }
  }
}

//=== PowerSpectrum.scala ===
@spatial object PowerSpectrum {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val realDram = DRAM[Float](n)
    val imagDram = DRAM[Float](n)
    val outDram  = DRAM[Float](n)
    Accel {
      val realSram = SRAM[Float](n)
      val imagSram = SRAM[Float](n)
      val outSram  = SRAM[Float](n)
      realSram load realDram
      imagSram load imagDram
      Foreach(n by 1) { i =>
        outSram(i) = realSram(i)*realSram(i) + imagSram(i)*imagSram(i)
      }
      outDram store outSram
    }
  }
}

//=== MelFilterbank.scala ===
@spatial object MelFilterbank {
  def main(args: Array[String]): Unit = {
    val bands = args(0).to[I32]    // e.g., 80
    val bins  = args(1).to[I32]    // e.g., n_fft/2+1
    val matDram = DRAM[Float](bands, bins)
    val vecDram = DRAM[Float](bins)
    val outDram = DRAM[Float](bands)
    Accel {
      val matSram = SRAM[Float](bands, bins)
      val vecSram = SRAM[Float](bins)
      val outSram = SRAM[Float](bands)
      matSram load matDram
      vecSram load vecDram
      Foreach(bands by 1) { i =>
        var acc = 0.to[Float]
        Foreach(bins by 1) { j =>
          acc = acc + matSram(i, j) * vecSram(j)
        }
        outSram(i) = acc
      }
      outDram store outSram
    }
  }
}

//=== LogCompress.scala ===
@spatial object LogCompress {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val dynRange = args(1).to[Float]  // e.g., 8.0f
    val inDram = DRAM[Float](n)
    val outDram = DRAM[Float](n)
    Accel {
      val buf = SRAM[Float](n)
      buf load inDram
      // Compute max
      val mx = Reduce(Max)(buf)(i => buf(i))
      Foreach(n by 1) { i =>
        buf(i) = max(buf(i), mx - dynRange)
      }
      Foreach(n by 1) { i =>
        outDram(i) = log10(max(buf(i), 1e-10.to[Float]))
      }
    }
  }
}

//=== WhisperScale.scala ===
@spatial object WhisperScale {
  def main(args: Array[String]): Unit = {
    val n = args(0).to[I32]
    val inDram = DRAM[Float](n)
    val outDram = DRAM[Float](n)
    Accel {
      val buf = SRAM[Float](n)
      buf load inDram
      Foreach(n by 1) { i =>
        buf(i) = (buf(i) + 4.to[Float]) / 4.to[Float]
      }
      outDram store buf
    }
  }
}

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

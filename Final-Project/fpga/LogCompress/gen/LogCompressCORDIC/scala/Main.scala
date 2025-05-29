import emul._
import emul.implicits._

object Main {
  def main(args: Array[String]): Unit = {
    OOB.open()
    val x832 = args
    if (args.contains("--help") || args.contains("-h")) { Main.printHelp() }
    // Commandline argument #FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) (<unnamed>)
    val x833 = try{ x832.apply(FixedPoint(BigDecimal("0"),FixFormat(true,32,0))) }
    catch {case _:Throwable =>
      println("Missing argument " + FixedPoint(BigDecimal("0"),FixFormat(true,32,0)) + " ('n_runtime')")
      Main.printHelp()
      sys.exit(-1)
    }
    val x834 = FixedPoint(x833, FixFormat(true,32,0))
    // Commandline argument #FixedPoint(BigDecimal("1"),FixFormat(true,32,0)) (<unnamed>)
    val x835 = try{ x832.apply(FixedPoint(BigDecimal("1"),FixFormat(true,32,0))) }
    catch {case _:Throwable =>
      println("Missing argument " + FixedPoint(BigDecimal("1"),FixFormat(true,32,0)) + " ('dynRange')")
      Main.printHelp()
      sys.exit(-1)
    }
    val x836 = FloatPoint(x835, FltFormat(23,8))
    val x837 = Array[FloatPoint](FloatPoint(BigDecimal("1"), FltFormat(23,8)), FloatPoint(BigDecimal("1.070866107940673828125"), FltFormat(23,8)), FloatPoint(BigDecimal("1.14173233509063720703125"), FltFormat(23,8)), FloatPoint(BigDecimal("1.21259844303131103515625"), FltFormat(23,8)), FloatPoint(BigDecimal("1.28346455097198486328125"), FltFormat(23,8)), FloatPoint(BigDecimal("1.35433065891265869140625"), FltFormat(23,8)), FloatPoint(BigDecimal("1.4251968860626220703125"), FltFormat(23,8)), FloatPoint(BigDecimal("1.4960629940032958984375"), FltFormat(23,8)), FloatPoint(BigDecimal("1.5669291019439697265625"), FltFormat(23,8)), FloatPoint(BigDecimal("1.63779532909393310546875"), FltFormat(23,8)), FloatPoint(BigDecimal("1.70866143703460693359375"), FltFormat(23,8)), FloatPoint(BigDecimal("1.77952754497528076171875"), FltFormat(23,8)), FloatPoint(BigDecimal("1.85039365291595458984375"), FltFormat(23,8)), FloatPoint(BigDecimal("1.92125988006591796875"), FltFormat(23,8)), FloatPoint(BigDecimal("1.992125988006591796875"), FltFormat(23,8)), FloatPoint(BigDecimal("2.0629920959472656250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.1338582038879394531250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.204724311828613281250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.27559065818786621093750"), FltFormat(23,8)), FloatPoint(BigDecimal("2.34645676612854003906250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.41732287406921386718750"), FltFormat(23,8)), FloatPoint(BigDecimal("2.48818898200988769531250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.55905508995056152343750"), FltFormat(23,8)), FloatPoint(BigDecimal("2.62992119789123535156250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.70078730583190917968750"), FltFormat(23,8)), FloatPoint(BigDecimal("2.7716536521911621093750"), FltFormat(23,8)), FloatPoint(BigDecimal("2.84251976013183593750"), FltFormat(23,8)), FloatPoint(BigDecimal("2.9133858680725097656250"), FltFormat(23,8)), FloatPoint(BigDecimal("2.984251976013183593750"), FltFormat(23,8)), FloatPoint(BigDecimal("3.0551180839538574218750"), FltFormat(23,8)), FloatPoint(BigDecimal("3.125984191894531250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.1968502998352050781250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.26771664619445800781250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.33858275413513183593750"), FltFormat(23,8)), FloatPoint(BigDecimal("3.40944886207580566406250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.48031497001647949218750"), FltFormat(23,8)), FloatPoint(BigDecimal("3.55118107795715332031250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.62204718589782714843750"), FltFormat(23,8)), FloatPoint(BigDecimal("3.69291329383850097656250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.763779640197753906250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.8346457481384277343750"), FltFormat(23,8)), FloatPoint(BigDecimal("3.90551185607910156250"), FltFormat(23,8)), FloatPoint(BigDecimal("3.9763779640197753906250"), FltFormat(23,8)), FloatPoint(BigDecimal("4.0472440719604492187500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.11811017990112304687500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.18897628784179687500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.25984239578247070312500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.3307085037231445312500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.40157461166381835937500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.472440719604492187500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.5433073043823242187500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.61417341232299804687500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.68503952026367187500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.75590562820434570312500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.8267717361450195312500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.89763784408569335937500"), FltFormat(23,8)), FloatPoint(BigDecimal("4.968503952026367187500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.03937005996704101562500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.1102361679077148437500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.18110227584838867187500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.251968383789062500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.32283449172973632812500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.3937005996704101562500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.46456670761108398437500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.53543329238891601562500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.6062994003295898437500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.67716550827026367187500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.748031616210937500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.81889772415161132812500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.8897638320922851562500"), FltFormat(23,8)), FloatPoint(BigDecimal("5.96062994003295898437500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.031496047973632812500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.10236215591430664062500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.1732282638549804687500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.24409437179565429687500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.31496047973632812500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.38582658767700195312500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.4566926956176757812500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.527559280395507812500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.59842538833618164062500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.6692914962768554687500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.74015760421752929687500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.81102371215820312500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.88188982009887695312500"), FltFormat(23,8)), FloatPoint(BigDecimal("6.9527559280395507812500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.02362203598022460937500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.094488143920898437500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.16535425186157226562500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.2362203598022460937500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.30708646774291992187500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.3779525756835937500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.44881868362426757812500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.51968526840209960937500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.590551376342773437500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.66141748428344726562500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.7322835922241210937500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.80314970016479492187500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.8740158081054687500"), FltFormat(23,8)), FloatPoint(BigDecimal("7.94488191604614257812500"), FltFormat(23,8)), FloatPoint(BigDecimal("8.01574802398681640625000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.0866146087646484375000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.1574802398681640625000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.22834682464599609375000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.29921245574951171875000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.37007904052734375000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.440944671630859375000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.51181125640869140625000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.58267688751220703125000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.6535434722900390625000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.7244091033935546875000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.79527568817138671875000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.86614131927490234375000"), FltFormat(23,8)), FloatPoint(BigDecimal("8.937007904052734375000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.00787448883056640625000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.07874011993408203125000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.1496067047119140625000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.2204723358154296875000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.29133892059326171875000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.36220455169677734375000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.433071136474609375000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.503936767578125000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.57480335235595703125000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.64566898345947265625000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.7165355682373046875000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.7874011993408203125000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.85826778411865234375000"), FltFormat(23,8)), FloatPoint(BigDecimal("9.92913341522216796875000"), FltFormat(23,8)), FloatPoint(BigDecimal("10.00"), FltFormat(23,8)))
    val x838 = Array[FloatPoint](FloatPoint("-0.0", FltFormat(23,8)), FloatPoint(BigDecimal("0.0297351740300655364990234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.0575642995536327362060546875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.08371700346469879150390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.10838387906551361083984375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.13172471523284912109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.15387485921382904052734375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.1749498844146728515625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.195049345493316650390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.214259624481201171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.23265601694583892822265625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.2503047287464141845703125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.2672641277313232421875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.2835861146450042724609375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.2993167936801910400390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.3144975602626800537109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.3291655480861663818359375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.343354284763336181640625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.3570941388607025146484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.3704125583171844482421875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.3833346664905548095703125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.3958833515644073486328125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.408079624176025390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.41994273662567138671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.4314903914928436279296875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.442738950252532958984375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.4537034928798675537109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.4643979966640472412109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.4748354852199554443359375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.4850279986858367919921875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.4949867725372314453125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.504722297191619873046875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.514244377613067626953125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.523562133312225341796875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.53268420696258544921875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.541618525981903076171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.5503728389739990234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.55895411968231201171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.56736910343170166015625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.575624167919158935546875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.583725273609161376953125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.591677963733673095703125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.59948766231536865234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.6071593761444091796875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.614697933197021484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.622107923030853271484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.629393517971038818359375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.636558949947357177734375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.64360809326171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.650544583797454833984375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.65737211704254150390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.664093911647796630859375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.670713245868682861328125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.677233219146728515625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.683656752109527587890625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.689986646175384521484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.696225643157958984375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.70237624645233154296875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.708440959453582763671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.714422166347503662109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.720322132110595703125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.726142942905426025390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.73188686370849609375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.7375557422637939453125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.743151605129241943359375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.748676300048828125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.7541315555572509765625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.759519159793853759765625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.76484072208404541015625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.77009785175323486328125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.7752921581268310546875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.78042507171630859375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.785497963428497314453125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.7905123233795166015625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.795469462871551513671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.800370633602142333984375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.80521714687347412109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.810010135173797607421875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.814750850200653076171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.819440305233001708984375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.824079692363739013671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.828670024871826171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.833212375640869140625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.837707698345184326171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.84215700626373291015625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.846561133861541748046875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.850921094417572021484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.855237662792205810546875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.859511792659759521484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.863744258880615234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.867935836315155029296875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.872087419033050537109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.876199662685394287109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.880273342132568359375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.884309113025665283203125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.888307750225067138671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.892269909381866455078125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.896196305751800537109375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.90008747577667236328125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.903944075107574462890625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.907766759395599365234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.9115560054779052734375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.915312588214874267578125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.919036865234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.922729551792144775390625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.926391065120697021484375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.9300220012664794921875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.933622777462005615234375"), FltFormat(23,8)), FloatPoint(BigDecimal("0.93719398975372314453125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.940735995769500732421875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.944249451160430908203125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.947734653949737548828125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.9511921405792236328125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.954622328281402587890625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.95802557468414306640625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.961402416229248046875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.96475315093994140625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.968078315258026123046875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.971378147602081298828125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.97465312480926513671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.977903544902801513671875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.9811298847198486328125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.984332382678985595703125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.987511456012725830078125"), FltFormat(23,8)), FloatPoint(BigDecimal("0.990667402744293212890625"), FltFormat(23,8)), FloatPoint(BigDecimal("0.993800640106201171875"), FltFormat(23,8)), FloatPoint(BigDecimal("0.996911346912384033203125"), FltFormat(23,8)), FloatPoint(BigDecimal("1"), FltFormat(23,8)))
    x839_argIn.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x840_set_x839 = x839_argIn.set(x834)
    val x841_rd_x839 = x839_argIn.value
    x842_inDram.initMem(x841_rd_x839 + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x843_outDram.initMem(x841_rd_x839 + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x844_xLutDram.initMem(FixedPoint(BigDecimal("128"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x845_yLutDram.initMem(FixedPoint(BigDecimal("128"),FixFormat(true,32,0)) + 16,FloatPoint("-0.0", FltFormat(23,8)))
    x846_rawLogOutDram.initMem(x841_rd_x839 + 16,FloatPoint("-0.0", FltFormat(23,8)))
    val x847 = {
      for (i <- 0 until x837.length) {
        try {
          try {
            x844_xLutDram(i) = x837(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:40:11 Memory xLutTable: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:40:11 Memory xLutDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x848 = {
      for (i <- 0 until x838.length) {
        try {
          try {
            x845_yLutDram(i) = x838(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:41:11 Memory yLutTable: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:41:11 Memory yLutDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x849 = {
      val file = new java.io.File("../../../../fpga_io/melfilterbank_output.csv")
      if (false) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x850 = {
      val scanner = new java.util.Scanner(x849)
      val tokens = new scala.collection.mutable.ArrayBuffer[String]() 
      scanner.useDelimiter("\\s*" + "\n" + "\\s*|\\s*\n\\s*")
      while (scanner.hasNext) {
        tokens += scanner.next.trim
      }
      scanner.close()
      tokens.toArray
    }
    val x854 = Array.tabulate(x850.length){bbb => 
      val b17 = FixedPoint.fromInt(bbb)
      val x852 = x850.apply(b17)
      val x853 = FloatPoint(x852, FltFormat(23,8))
      x853
    }
    val x855 = {
      for (i <- 0 until x854.length) {
        try {
          try {
            x842_inDram(i) = x854(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:44:11 Memory inputData: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:44:11 Memory inDram: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    x856_Accel_n.initMem(FixedPoint(BigDecimal("0"),FixFormat(true,32,0)))
    val x857_set_x856 = x856_Accel_n.set(x834)
    x858_argIn.initMem(FloatPoint("-0.0", FltFormat(23,8)))
    val x859_set_x858 = x858_argIn.set(x836)
    val x1256_outr_RootController = x1256_outr_RootController_kernel.run()
    val x1226 = new Array[FloatPoint](x834)
    val x1227 = {
      for (i <- 0 until x1226.length) {
        try {
          try {
            x1226(i) = x843_outDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:184:24 Memory outDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:184:24 Memory result: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x1228 = {
      val file = new java.io.File("../../../../fpga_io/logcompress_output.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x1229 = FixedPoint.fromInt(x1226.length)
    val x1232 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x1228, true /*append*/))
      (0 until x1229.toInt).foreach{b166 => 
        writer.write("\n")
        val x1230 = x1226.apply(b166)
        val x1231 = x1230.toString
        writer.write(x1231)
      }
      writer.close()
    }
    val x1234 = new Array[FloatPoint](x834)
    val x1235 = {
      for (i <- 0 until x1234.length) {
        try {
          try {
            x1234(i) = x846_rawLogOutDram(i)
          }
          catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
            System.out.println("[warn] LogCompress.scala:189:30 Memory rawLogOutDram: Out of bounds read at address " + err.getMessage)
            FloatPoint.invalid(FltFormat(23,8))
          }
        }
        catch {case err: java.lang.ArrayIndexOutOfBoundsException => 
          System.out.println("[warn] LogCompress.scala:189:30 Memory rawLogResult: Out of bounds write at address " + err.getMessage)
        }
      }
    }
    val x1236 = {
      val file = new java.io.File("../../../../fpga_io/logcompress_output_raw_fpga.csv")
      if (true) { // Will write to file
        val writer = new java.io.PrintWriter(file)
        writer.print("")
      }
      file
    }
    val x1237 = FixedPoint.fromInt(x1234.length)
    val x1240 = {
      val writer = new java.io.PrintWriter(new java.io.FileOutputStream(x1236, true /*append*/))
      (0 until x1237.toInt).foreach{b175 => 
        writer.write("\n")
        val x1238 = x1234.apply(b175)
        val x1239 = x1238.toString
        writer.write(x1239)
      }
      writer.close()
    }
    OOB.close()
  }
  def printHelp(): Unit = {
    System.out.print("Help for app: LogCompressCORDIC\n")
    System.out.print("  -- Args:    <0: n_runtime> <1: dynRange>\n");
    System.exit(0);
  }
}

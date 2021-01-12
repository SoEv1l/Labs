import java.math.BigInteger
import kotlin.math.pow
import kotlin.properties.Delegates

class Digit {
    var beta : Int
    var decimal : BigInteger
    var hex : String
    val binary = mutableListOf<Int>()
    val bigNum = mutableListOf<Int>()

    constructor(beta : Int, decimal : Long){
        this.beta = beta
        this.decimal = BigInteger.valueOf(decimal)
        this.hex = getHexFromDec(this.decimal)
        this.bigNum.addAll(getBigFromDec(this.decimal))
        this.binary.addAll(getBinFromDec(this.decimal))
    }
    constructor(beta : Int, hex : String){
        this.beta = beta
        this.hex = hex
        this.decimal = getDecFromHex(hex = hex)
        this.bigNum.addAll(getBigFromDec(this.decimal))
        this.binary.addAll(getBinFromDec(this.decimal))
    }
    constructor(beta : Int, bigNum : List<Int>){
        this.beta = beta
        this.bigNum.addAll(bigNum)
        this.decimal = getDecFromBig(bigNum = bigNum)
        this.hex = getHexFromDec(this.decimal)
        this.binary.addAll(getBinFromDec(this.decimal))
    }
    constructor(binary : List<Int>){
        beta = 2
        //print("HEX : ${getHexFromDec(BigInteger.valueOf(10))}")
        this.binary.addAll(binary)
        this.decimal = getDecFromBin(binary = binary)
        this.hex = getHexFromDec(this.decimal)
        this.bigNum.addAll(getBigFromDec(this.decimal))
    }

    private fun getHexFromDec(dec: BigInteger) : String {
        return dec.toString(16)
    }

    private fun getDecFromHex(hex : String) : BigInteger {
        return BigInteger(hex, 16)
    }

    private fun getBigFromDec(decimal : BigInteger) : List<Int>{
        var dec = decimal
        val bigNum = mutableListOf<Int>()
        while(dec > BigInteger.valueOf(0)){
            bigNum.add((dec.mod(BigInteger.valueOf(beta.toLong()))).toInt())
            dec = dec.divide(BigInteger.valueOf(beta.toLong()))
        }
        return bigNum.apply { reverse() }
    }

    private fun getDecFromBig(bigNum : List<Int>) : BigInteger{
        val big = mutableListOf<Int>().apply {
            addAll(bigNum)
            reverse()
        }
        var num = BigInteger.valueOf(0)
        for(i in 0 until big.size){
            val b = BigInteger.valueOf(beta.toLong())
            val power = b.pow(i)
            //println(power)
            val mul = BigInteger.valueOf(big[i].toLong()).multiply(power)
            num = num.add(mul)
            //print("$num\n")
        }
        //1.1235582092889474E307
            //num += big[i].toDouble().pow(beta).toLong()
        return num
    }

    private fun getBinFromDec(dec : BigInteger) : List<Int>{
        val binStr = dec.toString(2)
        val bin = mutableListOf<Int>()
        for(symbol in binStr) {
            bin.add(symbol.toString().toInt())
        }
        return bin
    }

    private fun getDecFromBin(binary : List<Int>) : BigInteger{
        val bin = mutableListOf<Int>().apply {
            addAll(binary)
            reverse()
        }
        var dec = BigInteger.valueOf(0)
        for(i in 0 until bin.size) {
            val pow = BigInteger.valueOf(2.0.pow(i).toLong())
            val mul = pow.multiply(BigInteger.valueOf(bin[i].toLong()))
            dec = dec.add(dec.add(mul))
            //dec.add(dec.add(BigInteger.valueOf(bin[i] * .toLong())))
        }
            //dec += bin[i] * 2.0.pow(i).toLong()
        return dec
    }
}
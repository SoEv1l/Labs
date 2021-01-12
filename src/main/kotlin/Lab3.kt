import java.math.BigInteger
import kotlin.math.pow

class Lab3 {
    val poli : String = "100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000010000000111"
    val m : Int = 173
    val pol = mapStringToBin(poli)

    fun mulMod(num1 : List<Int>, num2 : List<Int>): DivData {
        val arr = getLonger(num1, num2) //a > b
        var b = mutableListOf<Int>().apply {
            addAll(arr[0])
        }
        var a = mutableListOf<Int>().apply {
            addAll(arr[1])
        }
        var c = mutableListOf<Int>()
        b.reverse()
        for(i in 0 until b.size){
            var temp = lab1.mulOneDigit(a, b[i], 2)
            temp = lab1.shiftDigitsToHigh(temp, i)
            c = addLong(temp, c)
        }
        return divLong(c, pol)
    }

    fun mulLong(num1 : List<Int>, num2 : List<Int>): MutableList<Int> {
        val arr = getLonger(num1, num2) //a > b
        var a = mutableListOf<Int>().apply {
            addAll(arr[0])
        }
        var b = mutableListOf<Int>().apply {
            addAll(arr[1])
        }
        var c = mutableListOf<Int>()
        b.reverse()
        for(i in 0 until b.size){
            var temp = lab1.mulOneDigit(a, b[i], 2)
            temp = lab1.shiftDigitsToHigh(temp, i)
            c = addLong(temp, c)
        }
        return c
    }

    fun divLong(num1 : List<Int>, num2 : List<Int>): DivData {
        val k = num2.size
        var r = mutableListOf<Int>().apply { addAll(num1) }
        var q = mutableListOf<Int>()
        while(lab1.compare(r, num2)){
            var t = r.size
            var c = lab1.shiftDigitsToHigh(num2, t-k)
            r = addLong(r, c)
            q = lab1.insert(q, t-k)
            while(r.size != 0){
                if(r[0] == 0)
                    r.removeAt(0)
                else break
            }
        }
        return DivData(q, r)
    }

    fun sqr(num1: List<Int>): List<Int> {
        val a = mutableListOf<Int>().apply { addAll(num1) }
        for(i in 0 until a.size - 1)
            a.add(i * 2 + 1, 0)
        return divLong(a, pol).r
    }

    fun trace(num1 : List<Int>): MutableList<Int> {
        var a = mutableListOf<Int>().apply { addAll(num1) }
        var trace = mutableListOf(0)
        for(i in 0 until m){
            trace = addLong(trace, a)
            //println(trace)
            a = sqr(a) as MutableList<Int>
        }
        return trace
    }

    fun power(num1: List<Int>, n : List<Int>): MutableList<Int> {
        var a = mutableListOf<Int>().apply { addAll(num1) }
        var b = mutableListOf(1)
        var pow = mutableListOf<Int>().apply { addAll(n) }
        pow.reverse()
        for(i in 0 until pow.size){
            if(pow[i] == 1){
                b = mulLong(b, a)
                b = divLong(b, pol).r as MutableList<Int>
            }
            a = sqr(a) as MutableList<Int>
            a = divLong(a, pol).r as MutableList<Int>
        }
        return b
    }

    fun inv(num1 : List<Int>): MutableList<Int> {
        val n = BigInteger.valueOf(2).pow(m).subtract(BigInteger.valueOf(2))
        //var n = (2.0.pow(m)) - 2
        //var nDig = Digit(2, n.toLong())
        val nDig = mapBinFromDec(n)
        return power(nDig, num1)
    }

    fun addLong(num1 : List<Int>, num2 : List<Int>): MutableList<Int> {
        val arr = getLonger(num1, num2) //a > b
        var a = mutableListOf<Int>().apply {
            addAll(arr[0])
        }
        var b = mutableListOf<Int>().apply {
            addAll(arr[1])
        }
        var res = mutableListOf<Int>()
        a.reverse()
        b.reverse()
        for(i in 0 until a.size){
            var temp = 0
            if(b.size > i)
                temp = b[i]
            res.add((a[i] + temp) % 2)
        }
        res.reverse()
        return res
    }

}
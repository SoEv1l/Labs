import java.math.BigInteger
import kotlin.math.pow

class Lab4 {
    val m = 173

    fun add(num1 : List<Int>, num2 : List<Int>): MutableList<Int> {
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

    fun sqr(num1: List<Int>): List<Int> {
        val a = mutableListOf<Int>().apply { addAll(num1) }
        a.add(0, a[a.lastIndex])
        a.removeAt(a.lastIndex)
        return a
    }


    fun getMatrix(num1: List<Int>): MutableList<MutableList<Int>> {
        var m = num1.size
        var p = 2 * m + 1
        val matrix = mutableListOf<MutableList<Int>>()
        for(i in 0 until m){
            matrix.add(mutableListOf())
            for(j in 0 until m){
                if((BigInteger.valueOf(2).pow(i).add(BigInteger.valueOf(2).pow(j)).mod(BigInteger.valueOf(p.toLong())) == BigInteger.valueOf(1))
                            || (BigInteger.valueOf(2).pow(i).subtract(BigInteger.valueOf(2).pow(j)).mod(BigInteger.valueOf(p.toLong())) == BigInteger.valueOf(1))
                            || (BigInteger.valueOf(2).pow(j).subtract(BigInteger.valueOf(2).pow(i)).mod(BigInteger.valueOf(p.toLong())) == BigInteger.valueOf(1))
                            || (BigInteger.valueOf(2).pow(j).add(BigInteger.valueOf(2).pow(i)).multiply(BigInteger.valueOf(-1)).mod(BigInteger.valueOf(p.toLong())) == BigInteger.valueOf(1)))

                    matrix[i].add(1)
                else
                    matrix[i].add(0)
            }
        }
        return matrix
    }

    fun multiply(num1 : List<Int>, num2 : List<Int>): MutableList<Int> {
        var a = mutableListOf<Int>().apply { addAll(num1) }
        var b = mutableListOf<Int>().apply { addAll(num2) }
        val matrix = getMatrix(a)
        var d = mutableListOf<Int>()
        for(k in a.indices){
            var c = mutableListOf<Int>()
            for(i in a.indices){
                var summ = 0
                for(j in a.indices)
                    summ += a[j] * matrix[j][i]
                c.add(summ % 2)
            }
            var summ = 0
            for(i in c.indices)
                summ += c[i] * b[i]
            d.add(summ % 2)
            val x = a[0]
            a.removeAt(0)
            a.add(x)
            val y = b[0]
            b.removeAt(0)
            b.add(y)
        }
        return d
    }

    fun power(num1 : List<Int>, nn: List<Int>): List<Int> {
        val n = mutableListOf<Int>().apply { addAll(nn) }
        var a = mutableListOf<Int>().apply { addAll(num1) }
        var c = mutableListOf<Int>()
        for(i in num1.indices)
            c.add(1)
        n.reverse()
        for(i in n.indices){
            if(n[i] == 1)
                c = multiply(c, a)
            a = sqr(a) as MutableList<Int>
        }
        return c
    }

    fun inv(num1 : List<Int>): List<Int> {
        val m = num1.size
        val n = m - 1
        val nBin = mapBinFromDec(BigInteger.valueOf(n.toLong()))
        var k = 1
        var a = mutableListOf<Int>().apply { addAll(num1) }
        for(i in nBin.indices){
            if(i == 0)
                continue
            var num3 = sqr(a)
            num3 = power(a, mapBinFromDec(BigInteger.valueOf(2).pow(k)))
            a = multiply(num3, a)
            k *= 2
            if(nBin[i] == 1){
                a = sqr(a) as MutableList<Int>
                a = multiply(a, num1)
                k += 1
            }
        }
        a = sqr(a) as MutableList<Int>
        return a
    }

    fun trace(num1: List<Int>) : List<Int>{
        var a = mutableListOf<Int>().apply { addAll(num1) }
        var trace = mutableListOf<Int>(0)
        for(i in a.indices){
            trace = add(trace, a)
            a = sqr(a) as MutableList<Int>
        }
        return trace
    }
}
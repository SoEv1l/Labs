import java.math.BigInteger

class Lab2 {
    fun gcd(num1 : List<Int>, num2 : List<Int>) : List<Int>{
        val arr = getLonger(num1, num2) //a > b
        var a = mutableListOf<Int>().apply {
            addAll(arr[0])
        }
        var b = mutableListOf<Int>().apply {
            addAll(arr[1])
        }
        var d = mutableListOf(1)
        while (a[a.size - 1] == 0 && b[b.size - 1] == 0){
            a = lab1.div(a, mutableListOf(1,0)).q as MutableList<Int>
            b = lab1.div(b, mutableListOf(1,0)).q as MutableList<Int>
            d = lab1.mul(d, mutableListOf(1,0), 2) as MutableList<Int>
        }
        while(a[a.size - 1] == 0){
            a = lab1.div(a, mutableListOf(1,0)).q as MutableList<Int>
            if(a.isEmpty())
                a.add(0)
        }
        while (b != mutableListOf(0)){
            while (b[b.size - 1] == 0){
                b = lab1.div(b, mutableListOf(1, 0)).q as MutableList<Int>
                if(b.isEmpty())
                    b.add(0)
            }
            if(lab1.compare(a, b)){
                val aa = mutableListOf<Int>().apply { clear(); addAll(a) }
                a = mutableListOf<Int>().apply { clear(); addAll(b) }
                b = lab1.sub(aa, b, 2) as MutableList<Int>
            }
            else
                b = lab1.sub(b, a, 2) as MutableList<Int>
            if(b.size == 0)
                b.add(0)
        }
        d = lab1.mul(d, a, 2) as MutableList<Int>
        return d
    }

    fun lcm(num1 : List<Int>, num2 : List<Int>) : List<Int>{
        var a = mutableListOf<Int>().apply { addAll(num1) }
        var b = mutableListOf<Int>().apply { addAll(num2) }
        if(!lab1.compare(a, b)){
            a = mutableListOf<Int>().apply { addAll(num2) }
            b = mutableListOf<Int>().apply { addAll(num1) }
        }
        val gcdNum = gcd(a, b)
        a = lab1.div(a, gcdNum).q as MutableList<Int>
        println("a : ${a.getNumber(2).hex}")
        println("b : ${b.getNumber(2).hex}")
        val c = lab1.mul(b, a, 2)
        return c
    }

    fun barret(x : List<Int>, n : List<Int>, m : List<Int>): List<Int> {
        if(x.size != 2 * n.size)
            return lab1.div(x, n).r
        var q = killLastDigits(x, n.size - 1)
        q = lab1.mul(q, m, 2)
        q = killLastDigits(x, n.size + 1)
        var r = lab1.sub(x, lab1.mul(q, n, 2), 2)
        while (lab1.compare(r, n))
            r = lab1.sub(r, n, 2)
        return r
    }

    fun addMod(num1: List<Int>, num2: List<Int>, mod: List<Int>): List<Int> {
        val num4 = lab1.add(num1, num2, 2)
        val m = BigInteger.valueOf(2).pow(num4.size)//2.0.pow(num4.size)
        var mBin = getBinFromDec(m)
        mBin = lab1.div(mBin, mod).q
        return barret(num4, mod, mBin)
    }

    fun subMod(num1: List<Int>, num2: List<Int>, mod: List<Int>): List<Int> {
        val num4 = lab1.sub(num1, num2, 2)
        val m = BigInteger.valueOf(2).pow(num4.size)//2.0.pow(num4.size)
        var mBin = getBinFromDec(m)
        mBin = lab1.div(mBin, mod).q
        return barret(num4, mod, mBin)
    }

    fun mulMod(num1: List<Int>, num2: List<Int>, mod: List<Int>): List<Int> {
        val num4 = lab1.mul(num1, num2, 2)
        val m = BigInteger.valueOf(2).pow(num4.size)//2.0.pow(num4.size)
        var mBin = getBinFromDec(m)
        mBin = lab1.div(mBin, mod).q
        return barret(num4, mod, mBin)
    }

    fun gorner(num1 : List<Int>, num2 : List<Int>, n : List<Int>): List<Int> {
        var a = mutableListOf<Int>().apply { addAll(num1) }
        val b = mutableListOf<Int>().apply { addAll(num2) }
        var c = mutableListOf<Int>(1)
        val m = BigInteger.valueOf(2).pow(n.size)
        var mBin = getBinFromDec(m)
        mBin = lab1.div(mBin, n).q
        b.reverse()
        for(i in 0 until b.size){
            if(b[i] == 1)
                c = barret(lab1.mul(c, a, 2), n, mBin) as MutableList<Int>
            a = barret(lab1.mul(a, a, 2), n, mBin) as MutableList<Int>
        }
        return c
    }

    fun killLastDigits(x : List<Int>, k : Int) : List<Int>{
        val a = mutableListOf<Int>().apply { addAll(x) }
        if(a.isEmpty()) return a
        for(i in 0 until k)
            a.remove(a[a.size - i])
        return a
    }

    private fun getBinFromDec(dec : BigInteger) : List<Int>{
        val binStr = dec.toString(2)
        val bin = mutableListOf<Int>()
        for(symbol in binStr) {
            bin.add(symbol.toString().toInt())
        }
        return bin
    }
}
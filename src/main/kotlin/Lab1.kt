class Lab1{

    fun add(num1 : List<Int>, num2 : List<Int>, beta : Int) : List<Int> {
        val arr = getLonger(num1, num2)
        val a = mutableListOf<Int>().apply {
            addAll(arr[0])
            reverse()
        }
        val b = mutableListOf<Int>().apply {
            addAll(arr[1])
            reverse()
        }
        val c = mutableListOf<Int>()
        var carry = 0
        for(i in 0 until a.size){
            var sec = 0
            if(b.size > i)
                sec = b[i]
            val temp = a[i] + sec + carry
            c.add(temp % beta)
            carry = temp / beta
        }
        if(carry != 0)
            c.add(carry)
        return c.apply { reverse() }
    }

    fun sub(num1: List<Int>, num2: List<Int>, beta : Int) : List<Int>{
        val arr = getLonger(num1, num2)
        val a = mutableListOf<Int>().apply {
            addAll(arr[0])
            reverse()
        }
        val b = mutableListOf<Int>().apply {
            addAll(arr[1])
            reverse()
        }
        val c = mutableListOf<Int>()
        var borrow = 0
        for(i in 0 until a.size) {
            var sec = 0
            if (b.size > i)
                sec = b[i]
            val temp = a[i] - sec - borrow
            borrow = if (temp >= 0) {
                c.add(temp)
                0
            } else {
                c.add(temp + beta)
                1
            }
        }
        c.reverse()
        while(c.size != 0){
            if(c[0] == 0)
                c.remove(element = 0)
            else break
        }
        return c
    }

    fun mul(num1: List<Int>, num2: List<Int>, beta : Int) : List<Int>{
        val arr = getLonger(num1, num2)
        //println("arr ${arr[0].size}, arr2 : ${arr[1].size} ")
        val a = mutableListOf<Int>().apply { addAll(arr[0]) }
        val b = mutableListOf<Int>().apply {
            addAll(arr[1])
            reverse()
        }
        var c = mutableListOf<Int>()
        for(i in 0 until b.size){
            var temp = mulOneDigit(a, b[i], beta)
            temp = shiftDigitsToHigh(temp, i)
            c = mutableListOf<Int>().apply { addAll(add(temp, c, beta)) }
        }
        //println("Mul c : $c")
        return c
    }

    fun div(num1: List<Int>, num2: List<Int>) : DivData{
        val k = num2.size
        var r = num1
        //println("$k, ${r.size}")
        var q = mutableListOf<Int>()
        while(compare(r, num2)){
            var t = r.size
            var c = shiftDigitsToHigh(num2, t - k)
            if(!compare(r, c)){
                //println("not compare")
                t -= 1
                c = shiftDigitsToHigh(num2, t - k)
            }
            r = sub(r, c, 2)
            q = insert(q, t - k)
            //println(q.size)
            //println("for")
        }
        //println("endfor")
        return DivData(q, r)
    }

    fun power(num1: List<Int>, num2: List<Int>) : List<Int>{
        var num3 = mutableListOf(1)
        val a = mutableListOf<Int>().apply {
            addAll(num2)
            //reverse()
        }
        var b = mutableListOf<Int>().apply { addAll(num1) }
        println(a)
        for(num in a){
            if(num == 1) {
                num3 = mutableListOf<Int>().apply { addAll(mul(num3, b, 2).getNumber(2).binary) }
               // println("num = 1 : $num3")
            }
            b = mutableListOf<Int>().apply { addAll(mul(b, b, 2).getNumber(2).binary) }
        }
        return num3
    }

    fun insert(num1: MutableList<Int>, pos : Int) : MutableList<Int>{
        var a = mutableListOf<Int>().apply { addAll(num1) }
        if(a.size <= pos){
            a.add(1)
            a = shiftDigitsToHigh(a, pos)
        }
        else{
            a.reverse()
            a[pos] = 1
            a.reverse()
        }
//        println("num1 : $num1")
//        println("a : $a")
        return a
    }

    fun compare(num1: List<Int>, num2: List<Int>) : Boolean{
        //println("num1 : ${num1.size}, num2 : ${num2.size}")
        when {
            num1.size > num2.size -> return true
            num1.size < num2.size -> return false
            else -> {
                for(i in num2.indices) {
                    //println("num1 : ${num1[i]}, num2 : ${num2[i]}")
                    if(num1[i] > num2[i]) return true
                    if(num1[i] < num2[i]) return false
                }
                return true
            }
        }
    }

    fun mulOneDigit(num1 : List<Int>, const : Int, beta : Int): List<Int> {
        val a = mutableListOf<Int>().apply {
            addAll(num1)
            reverse()
        }
        var carry = 0
        val res = mutableListOf<Int>()
        for(i in 0 until a.size){
            val temp = a[i] * const + carry
            res.add(temp % beta)
            carry = temp / beta
        }
        if(carry != 0)
            res.add(carry)
        return res.apply { reverse() }
    }

    fun shiftDigitsToHigh(num : List<Int>, count : Int): MutableList<Int> {
        val new = mutableListOf<Int>().apply { addAll(num) }
        for(i in 0 until count)
            new.add(0)
        return new
    }

}

data class DivData(val q : List<Int>, val r : List<Int>)
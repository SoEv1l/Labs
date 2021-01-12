import java.math.BigInteger

fun getLonger(num1 : List<Int>, num2 : List<Int>) : List<List<Int>>{
    var a = mutableListOf<Int>().apply { addAll(num1) }
    var b = mutableListOf<Int>().apply { addAll(num2) }
    if(a.size < b.size){
        val x = a
        a = b
        b = x
    }
    return listOf<List<Int>>(a, b)
}

fun mapStringToBin(a : String): List<Int> {
    val res = mutableListOf<Int>()
    for(ch in a)
        res.add(ch.toString().toInt())
    return res
}

fun mapBinToString(a : List<Int>) : String {
    var res = ""
    for(bit in a)
        res += bit.toString()
    return res
}

fun mapBinFromDec(dec : BigInteger) : List<Int>{
    val binStr = dec.toString(2)
    val bin = mutableListOf<Int>()
    for(symbol in binStr) {
        bin.add(symbol.toString().toInt())
    }
    return bin
}



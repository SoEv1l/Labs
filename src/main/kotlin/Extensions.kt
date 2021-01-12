fun List<Int>.getNumber(beta : Int) : Digit{
    return Digit(beta, this)
}
fun List<Int>.getNumber() : Digit{
    return Digit(this)
}
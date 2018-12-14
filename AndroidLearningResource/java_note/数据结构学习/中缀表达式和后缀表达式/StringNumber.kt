package shujujiegou

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018/12/12
 * description: 将文本数字转化为float数字
 */
class StringNumber(private var charArray:CharArray,private var floatVar:Float) {

    constructor(float: Float) : this(charArrayOf(),float) {
        floatVar = float
    }

    constructor(string: String) : this(string.toCharArray(),0.0f) {
        charArray = string.toCharArray()
    }

    init {
        if (charArray.size > 0) {
            var size = charArray.size
            var result = 0.0f
            charArray.forEachIndexed { index: Int, c: Char ->
                result += x10(c, size - index - 1)
            }
            floatVar = result
        }
    }

    fun value(): Float {
        return floatVar
    }

    private fun x10(c: Char, n: Int): Float {
        var result:Float = c.toString().toInt().toFloat()
        if (n == 0) {
            return result;
        }
        for (x in 0 until n) {
            result *= 10
        }
        return result
    }

    fun add(stringNumber: StringNumber): StringNumber {
        var float = floatVar + stringNumber.value()
        return StringNumber(float)
    }

    fun sub(stringNumber: StringNumber): StringNumber {
        var float = floatVar - stringNumber.value()
        return StringNumber(float)
    }

    fun multi(stringNumber: StringNumber): StringNumber {
        var float = floatVar * stringNumber.value()
        return StringNumber(float)
    }

    fun divide(stringNumber: StringNumber): StringNumber {
        var float = floatVar / stringNumber.value()
        return StringNumber(float)
    }
}
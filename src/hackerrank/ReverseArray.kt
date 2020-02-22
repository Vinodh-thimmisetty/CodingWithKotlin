package hackerrank

/**
 * https://kotlinlang.org/docs/reference/scope-functions.html
 *
 * @author Vinodh Kumar Thimmisetty
 *
 */
fun main() {
    val input = readLine()?.split(' ')!!.map { it.toInt() }.toIntArray()
    reverse(input.copyOf())
    println(input.contentToString())
    reversePrint(input.copyOf())
    reverseReadAndPrint()
}

fun reverseReadAndPrint() {
    println(readLine()?.split(Regex("\\s+"))!!.asReversed().joinToString(separator = " "))
}

fun reverse(input: IntArray) {
    (0 until input.size.div(2)).forEach { eit ->
        println("Current Index : $eit swapped with : ${input.size - 1 - eit}")
        val aSize = input.size - 1
        input[eit] = input[aSize - eit].apply {
            input[aSize - eit] = input[eit]
        }
    }
}

fun reversePrint(input: IntArray) {
    input.forEach { print("${input[it]} ") }.apply { this.toString().trimEnd() }
    println()
}
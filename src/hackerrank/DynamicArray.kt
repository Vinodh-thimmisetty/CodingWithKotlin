package hackerrank

/**
 *
 * @author Vinodh Kumar Thimmisetty
 *
 */


fun dynamicArray(n: Int, queries: Array<Array<Int>>): Array<Int> {
    var lastAnswer = 0
    val response = mutableListOf<Int>()
    val sequences = mutableListOf<MutableList<Int>>()
    repeat(n) { sequences.add(mutableListOf()) }
    repeat(queries.size) {
        val (type, xVal, yVal) = queries[it]
        val sequenceIndex = sequenceIndexFunc(xVal, lastAnswer, n)
        when (type) {
            1 -> sequences[sequenceIndex].add(yVal)
            2 -> {
                lastAnswer = sequences[sequenceIndex][yVal.rem(sequences[sequenceIndex].size)]
                response.add(lastAnswer)
            }
            else -> throw IllegalArgumentException()
        }
    }
    return response.toTypedArray()
}

fun sequenceIndexFunc(input: Int, a: Int, b: Int): Int = input.xor(a).rem(b)
fun main(args: Array<String>) {
    val firstMultipleInput = readLine()!!.trimEnd().split(" ")
    val n = firstMultipleInput[0].toInt()
    val q = firstMultipleInput[1].toInt()

    val queries = Array(q) { Array(3) { 0 } }

    for (i in 0 until q) {
        queries[i] = readLine()!!.trimEnd().split(" ").map { it.toInt() }.toTypedArray()
    }

    val result = dynamicArray(n, queries)

    println(result.joinToString("\n"))
}

fun main1() {
    var lastAnswer = 0
    val (n, q) = readLine()!!.split(regex = Regex("\\s+"), limit = 2).map { it.toInt() }
    val sequences = mutableListOf<MutableList<Int>>()
    repeat(n) { sequences.add(mutableListOf()) }
    repeat(q) {
        val (type, xVal, yVal) = readLine()!!.split(regex = Regex("\\s+"), limit = 3).map { it.toInt() }
        val sequenceIndex = sequenceIndexFunc(xVal, lastAnswer, n)
        when (type) {
            1 -> sequences[sequenceIndex].add(yVal)
            2 -> {
                lastAnswer = sequences[sequenceIndex][yVal.rem(sequences[sequenceIndex].size)]
                println(lastAnswer)
            }
            else -> throw IllegalArgumentException()
        }
    }

}
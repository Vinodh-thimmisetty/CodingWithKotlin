package hackerrank

import kotlin.math.max

/**
 *
 * @author Vinodh Kumar Thimmisetty
 *
 */

fun main() {
    val (rows: Int, cols: Int) = readLine()?.split(' ')!!.map { it.toInt() }
    val array2D = Array(size = rows) { IntArray(size = cols) }
    (0 until rows).forEach { row: Int -> array2D[row] = readLine()?.split(' ')!!.map { it.toInt() }.toIntArray() }
    println(array2D.contentDeepToString())
    var maxSum: Int = Int.MIN_VALUE
    (0 until rows - 2 step 1).forEach { row ->
        (0 until cols - 2 step 1).forEach { col ->
            maxSum = max(
                maxSum, arrayOf(
                    array2D[row][col], array2D[row][col + 1], array2D[row][col + 2],
                    array2D[row + 1][col + 1],
                    array2D[row + 2][col], array2D[row + 2][col + 1], array2D[row + 2][col + 2]
                ).sum()
            )
            println("$row : $col has Max Sum :: $maxSum")
        }
    }
    println("Max Hour glass sum ::  $maxSum")
}


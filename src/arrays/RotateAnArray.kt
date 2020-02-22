package arrays

import java.util.Collections.swap

/**
 *
 * @author Vinodh Kumar Thimmisetty
 *
 */
fun main() {
    val input = readLine()!!.split(' ').map { it.toInt() }.toIntArray()
    val noOfRotations = readLine()!!.toInt()
    tempArrayApproach(input, noOfRotations)
    withoutTempArray(input.toMutableList(), noOfRotations)
    reverseArrayApproach(input.toMutableList(), noOfRotations)
}

fun tempArrayApproach(input: IntArray, noOfRotations: Int) {
    val tempArray = IntArray(input.size)
    (0 until noOfRotations).forEach { ind: Int -> tempArray[(input.size - noOfRotations) + ind] = input[ind] }
    (0 until (input.size - noOfRotations)).forEach { ind: Int -> tempArray[ind] = input[noOfRotations + ind] }
    println(tempArray.contentToString())
}

fun withoutTempArray(input: MutableList<Int>, noOfRotations: Int): Array<Int> {
    (noOfRotations until input.size).forEach { eit ->
        (0 until noOfRotations).forEach { iit ->
            swap(input, eit - iit, eit - iit - 1)
        }
    }
    println(input.toString())
    return input.toTypedArray()
}

fun reverseArrayApproach(input: MutableList<Int>, noOfRotations: Int) {
    (0 until input.size / 2).forEach { swap(input, it, input.size - 1 - it) }
    println(input.toString())
}





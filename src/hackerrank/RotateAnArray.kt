package hackerrank

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

// Takes more time for large input :: Avoid it
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
    reverseSubList(input, 0, input.size - 1)
    println(input.toString())
    reverseSubList(input, 0, noOfRotations - 1)
    println(input.toString())
    reverseSubList(input, noOfRotations, input.size - 1)
    println(input.toString())
}

fun reverseSubList(input: MutableList<Int>, start: Int, end: Int) =
    (0..(end - start).div(2)).forEach { swap(input, start + it, end - it) }


class Rotate(var nums: IntArray, var start: Int, var end: Int) {
    fun reverse(): IntArray {
        while (start < end) {
            val temp = nums[end]
            nums[end--] = nums[start]
            nums[start++] = temp
        }
        return nums
    }
}

// Leetcode stub
class Solution {
    fun rotate(nums: IntArray, k: Int) {
        Rotate(
            Rotate(
                Rotate(nums, 0, nums.size - 1)
                    .reverse(), 0, k.rem(nums.size) - 1
            ).reverse(), k.rem(nums.size), nums.size - 1
        ).reverse()
    }

}





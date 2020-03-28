package coursera

import java.util.logging.Level
import java.util.logging.Logger

@Suppress("unused")
const val THREE_SUM_STATEMENT = """
    Given an array of integers, find all triplets in the array that sum up to a given target value i.e., 
    Triple(a, b, c) such that a + b + c = target.
    3-SUM in quadratic time. Design an algorithm for the 3-SUM problem that takes time proportional to n^2
    in the worst case.
"""

val input_array = arrayOf(30, -40, -20, -10, 40, 0, 10, 5, 20, -30, -50)
const val output = 0

val logger: Logger = Logger.getAnonymousLogger()

fun main() {
    logger.level = Level.SEVERE
    val bruteForce = System.currentTimeMillis()
    println(bruteForceThreeSum(input_array))
    println(System.currentTimeMillis() - bruteForce)
    val bSearch = System.currentTimeMillis()
    println(binarySearchThreeSum(input_array))
    println(System.currentTimeMillis() - bSearch)
    val slidingWindow = System.currentTimeMillis()
    println(slidingWindowTechniqueThreeSum(input_array))
    println(System.currentTimeMillis() - slidingWindow)
    val hashing = System.currentTimeMillis()
    println(hashingThreeSum(input_array))
    println(System.currentTimeMillis() - hashing)
}

fun bruteForceThreeSum(input: Array<Int>): Int {
    input.sort()
    var counter = 0
    val inputSize = input.size
    for (first in 0 until inputSize) {
        for (second in first + 1 until inputSize) {
            for (third in second + 1 until inputSize) {
                if (input_array[first] + input_array[second] + input_array[third] == output) {
                    logger.info("${input_array[first]}  ${input_array[second]}  ${input_array[third]} = $output")
                    counter++
                }
            }
        }
    }
    return counter
}

fun binarySearchThreeSum(input: Array<Int>): Int {
    var counter = 0
    val inputSize = input.size
    input.sort() // BST requires Array in ASC order
    for (first in 0 until inputSize) {
        for (second in first + 1 until inputSize) {
            val currentSum = input_array[first] + input_array[second]
            val searchFor = output - currentSum
            val remainingInput = input.sliceArray(IntRange(second + 1, inputSize - 1))
            if (binarySearch(searchFor, remainingInput) != -1) {
                logger.info("Current Sum is $currentSum, so search for $searchFor in ${remainingInput.contentToString()}")
                counter++
            }
        }
    }
    return counter
}

fun binarySearch(searchFor: Int, input: Array<Int>): Int {
    val inputSize = input.size
    var low = 0
    var high = inputSize - 1

    while (low <= high) {
        val mid = low + (high - low) / 2
        when {
            searchFor < input[mid] -> {
                high = mid - 1
            }
            searchFor > input[mid] -> {
                low = mid + 1
            }
            else -> {
                logger.info("$searchFor found at index $mid")
                return mid
            }
        }
    }
    logger.info("$searchFor doesn't exists in given ${input.contentToString()} array")
    return -1
}

fun slidingWindowTechniqueThreeSum(input: Array<Int>): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    var counter = 0
    val inputSize = input.size
    input.sort()
    for (first in 0 until inputSize) {
        val searchFor = output - input[first]
        var start = first + 1
        var end = inputSize - 1

        while (start < end) {
            val curSum = input[start] + input[end]
            when {
                curSum == searchFor -> {
                    logger.info("current val :: ${input[first]} = ${input[start]} + ${input[end]}")
                    counter++
                    result.add(listOf(input[first], input[start], input[end]))
                    start++
                    end--
                }
                curSum < searchFor -> {
                    start++
                }
                curSum > searchFor -> {
                    end--
                }
            }
        }
    }
    return result.distinct()
}

fun hashingThreeSum(input: Array<Int>): Int {
    var counter = 0
    val inputSize = input.size
    (0 until inputSize).forEach { first ->
        val tempSet = mutableSetOf<Int>()
        (first + 1 until inputSize).forEach { second ->
            val searchFor = output - input[first] - input[second]
            if (tempSet.contains(searchFor)) {
                counter++
            } else {
                tempSet.add(input[second])
            }
        }

    }
    return counter
}
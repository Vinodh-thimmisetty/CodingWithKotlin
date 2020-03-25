package trees.karumanchi

import trees.BNode
import trees.karumanchi.TYPE.MAX
import trees.karumanchi.TYPE.MIN
import trees.rootNode
import trees.sampleTree
import java.util.*

@Suppress("unused")
const val PROBLEM_STATEMENT = """
    Find the MAX/MIN element in Binary Tree - Recursive and Iterative solution.
"""

fun main() {
    sampleTree()
    println(findRecursiveType(rootNode))
    println(findIterativeType(rootNode))
}

enum class TYPE {
    MIN, MAX
}

fun findRecursiveType(
    rootNode: BNode?,
    type: TYPE = MAX
): Int {
    var leftTreeVal: Int
    var rightTreeVal: Int
    var rootNodeVal: Int
    var currentResult: Int = defaultVal(type)

    rootNode?.run {
        rootNodeVal = data
        leftTreeVal = findRecursiveType(left)
        rightTreeVal = findRecursiveType(right)
        currentResult = calculate(type, leftTreeVal, rightTreeVal, rootNodeVal)
        println("For Node $rootNodeVal, rootVal - $rootNodeVal, leftMax - $leftTreeVal, rightMax - $rightTreeVal. Max := $currentResult")
        // return calculate(type, findMaxRecursive(left), findMaxRecursive(right), data)
    }

    return currentResult
}

fun findIterativeType(rootNode: BNode?, type: TYPE = MAX): Int {
    val tempQueue = ArrayDeque<BNode>() as Queue<BNode>
    tempQueue.add(rootNode)
    var currentMax = Int.MIN_VALUE
    while (tempQueue.isNotEmpty()) {
        with(tempQueue.remove()) {
            currentMax = calculate(type, currentMax, data)
            tempQueue.apply {
                left?.run { add(this) }
                right?.run { add(this) }
            }
        }
    }
    return currentMax
}

fun calculate(type: TYPE, vararg input: Int) =
    when (type) {
        MAX -> input.max()
        MIN -> input.min()
    }!!

fun defaultVal(type: TYPE) =
    when (type) {
        MAX -> Int.MIN_VALUE
        MIN -> Int.MAX_VALUE
    }
package coursera

import edu.princeton.cs.algs4.StdStats
import edu.princeton.cs.algs4.WeightedQuickUnionUF
import kotlin.math.sqrt
import kotlin.random.Random

@Suppress("unused")
const val PROBLEM_STATEMENT = """
    Programming Assignment: Percolation
    Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.
    https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
"""


fun main() {
    with(PercolationStats(20, 30)) {
        performTrails()
        println("mean                    = ${mean()}")
        println("stddev                  = ${stdDeviation()}")
        println("95% confidence interval = [${confidenceLow()} , ${confidenceHigh()}]")
    }
}

enum class Direction {
    LEFT, RIGHT, TOP, BOTTOM
}

class Percolation(private var noOfSites: Int) {
    private var openSites = 0
    private var wquf = WeightedQuickUnionUF(noOfSites * noOfSites)
    private var twoDimArray = Array(noOfSites) { Array(noOfSites) { -1 } }

    private fun siteNumber(row: Int, col: Int) = noOfSites * row + col
    private fun siteParent(row: Int, col: Int) = wquf.find(siteNumber(row, col))

    fun open(row: Int, col: Int) {
        validateIndex(row)
        validateIndex(col)
        if (!isOpen(row, col)) {
            twoDimArray[row][col] = 0
            openSites++
            checkUnion(Direction.LEFT, row, col)
            checkUnion(Direction.RIGHT, row, col)
            checkUnion(Direction.TOP, row, col)
            checkUnion(Direction.BOTTOM, row, col)
        }
    }

    private fun checkUnion(direction: Direction, fromRow: Int, fromCol: Int) {
        var toRow: Int = fromRow
        var toCol: Int = fromCol
        when (direction) {
            Direction.LEFT -> {
                toRow = fromRow
                toCol = fromCol - 1
            }
            Direction.RIGHT -> {
                toRow = fromRow
                toCol = fromCol + 1
            }
            Direction.TOP -> {
                toRow = fromRow - 1
                toCol = fromCol
            }
            Direction.BOTTOM -> {
                toRow = fromRow + 1
                toCol = fromCol
            }
        }
        try {
            if (isOpen(toRow, toCol)) {
                wquf.union(siteNumber(fromRow, fromCol), siteNumber(toRow, toCol))
            }
        } catch (ex: IllegalArgumentException) {
            // Ignore any exceptions.
        }
    }

    private fun isOpen(row: Int, col: Int): Boolean {
        validateIndex(row)
        validateIndex(col)
        return twoDimArray[row][col] != -1
    }

    private fun isFull(row: Int, col: Int): Boolean {
        validateIndex(row)
        validateIndex(col)
        val currentParent = siteParent(row, col)
        // Check the parent of any TOP ROW is in same group as current (row,col)
        for (firstRow in 0 until noOfSites) {
            if (isOpen(0, firstRow) && siteParent(0, firstRow) == currentParent) {
                return true
            }
        }
        return false
    }

    fun numberOfOpenSites(): Int = openSites

    fun percolates(): Boolean {
        // Parent of any bottom row is same as top row then percolates
        for (lastRow in 0 until noOfSites) {
            if (isFull(noOfSites - 1, lastRow)) {
                return true
            }
        }
        return false
    }

    private fun validateIndex(index: Int) {
        if (index < 0 || index >= noOfSites)
            throw IllegalArgumentException("index $index is not between 0 and ${noOfSites - 1}")
    }
}

class PercolationStats(private val noOfSites: Int, private val noOfTestCases: Int) {
    private var results = Array(noOfTestCases) { 0.0 }

    fun performTrails() {
        repeat(noOfTestCases) {
            Percolation(noOfSites).run {
                while (!percolates()) {
                    open(Random.nextInt(noOfSites), Random.nextInt(noOfSites))
                }
                println("$it percolation is possible with ${numberOfOpenSites()}")
                results[it] = numberOfOpenSites().div((noOfSites * noOfSites).toDouble())
            }
        }
    }

    fun mean() = StdStats.mean(results.toDoubleArray())

    fun stdDeviation() = StdStats.stddev(results.toDoubleArray())

    fun confidenceLow() = mean().minus((1.96.times(stdDeviation())).div(sqrt(noOfTestCases.toDouble())))

    fun confidenceHigh() = mean().plus((1.96.times(stdDeviation())).div(sqrt(noOfTestCases.toDouble())))

}

@Suppress("unused")
fun sampleTestCase() {

    val sites = Percolation(6)
    sites.open(0, 4)
    sites.open(0, 3)
    sites.open(0, 5)
    sites.open(0, 1)
    println(sites.numberOfOpenSites())
    sites.open(1, 2)
    sites.open(1, 0)
    sites.open(1, 4)
    sites.open(4, 0)

    println(sites.numberOfOpenSites())
    sites.open(2, 4)
    sites.open(2, 2)
    sites.open(2, 1)
    sites.open(2, 3)
    println(sites.numberOfOpenSites())

    sites.open(3, 0)
    sites.open(3, 5)
    sites.open(3, 4)
    println(sites.numberOfOpenSites())

    sites.open(4, 1)
    sites.open(4, 4)
    sites.open(4, 3)
    sites.open(4, 2)
    println(sites.numberOfOpenSites())

    sites.open(5, 2)
    sites.open(5, 5)
    sites.open(5, 0)
    println(sites.numberOfOpenSites())

    println(sites.percolates())

}
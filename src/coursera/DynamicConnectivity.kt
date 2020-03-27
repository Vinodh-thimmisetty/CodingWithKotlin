package coursera

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ForkJoinPool

val threadPoolDispatcher = ForkJoinPool.commonPool().asCoroutineDispatcher()

fun main() {
    runBlocking {
        launch(threadPoolDispatcher) {
            dynamicConnection(QuickFind(Array(10) { it }) as DynamicConnectivity)
            dynamicConnection(QuickUnion(Array(10) { it }) as DynamicConnectivity)
            dynamicConnection(WeightedQuickUnion(Array(10) { it }) as DynamicConnectivity)
        }
    }
}

fun dynamicConnection(dynamicConnectivity: DynamicConnectivity) {
    val noOfQueries = readLine()?.trimEnd()!!.toInt()
    with(dynamicConnectivity) {
        (0 until noOfQueries).forEach { _ ->
            val (p, q) = readLine()?.split(' ')!!.map { it.toInt() }
            if (!isConnected(p, q)) {
                connect(p, q)
                println("Connecting from $p to $q")
            } else {
                println("Connection already exists from $p to $q")
            }
            friendsInfo()
        }
    }
}

interface DynamicConnectivity {
    fun isConnected(source: Int, destination: Int): Boolean
    fun connect(source: Int, destination: Int)
    fun friendsInfo()
}

class QuickFind(private var friends: Array<Int>) : DynamicConnectivity {

    override fun isConnected(source: Int, destination: Int) = friends[source] == friends[destination]

    override fun connect(source: Int, destination: Int) {
        val curSrc = friends[source]
        val curDest = friends[destination]
        // Replace all with source
        for ((i, v) in friends.withIndex()) {
            if (v == curDest) {
                friends[i] = curSrc
            }
        }
    }

    override fun friendsInfo() = println(friends.contentToString())
}

class QuickUnion(private var friends: Array<Int>) : DynamicConnectivity {
    override fun isConnected(source: Int, destination: Int): Boolean = findRoot(source) == findRoot(destination)
    override fun connect(source: Int, destination: Int) {
        friends[findRoot(source)] = findRoot(destination)
    }

    override fun friendsInfo() = println(friends.contentToString())

    private fun findRoot(input: Int): Int {
        var curTargetIndex = input
        var curTargetVal = friends[input]
        while (curTargetIndex != curTargetVal) {
            curTargetIndex = friends[curTargetIndex]
            curTargetVal = friends[curTargetIndex]
        }
        return curTargetIndex
    }

}

class WeightedQuickUnion(private var friends: Array<Int>) : DynamicConnectivity {
    private var treeSize = Array(10) { 1 }
    override fun isConnected(source: Int, destination: Int): Boolean = findRoot(source) == findRoot(destination)
    override fun connect(source: Int, destination: Int) {
        val srcParent = findRoot(source)
        val destParent = findRoot(destination)
        if (srcParent == destParent) return
        if (treeSize[srcParent] < treeSize[destParent]) {
            friends[srcParent] = destParent // change the root for smaller tree
            treeSize[destParent] += treeSize[srcParent]
        } else {
            friends[destParent] = srcParent
            treeSize[srcParent] += treeSize[destParent]
        }
    }

    override fun friendsInfo() = println("${friends.contentToString()} - ${treeSize.contentToString()}")

    private fun findRoot(input: Int): Int {
        var curTargetIndex = input
        var curTargetVal = friends[input]
        while (curTargetIndex != curTargetVal) {
            curTargetIndex = friends[curTargetIndex]
            curTargetVal = friends[curTargetIndex]
            friends[curTargetIndex] = friends[friends[curTargetIndex]]
        }
        return curTargetIndex
    }

}
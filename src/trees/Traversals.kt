package trees

import java.util.*

fun main() {
    sampleTree()
    val tempNode = rootNode
    preOrderTraversalRecursive(tempNode)
    println()
    inOrderTraversalRecursive(tempNode)
    println()
    postOrderTraversalRecursive(tempNode)
    println()
    preOrderTraversalIterative(tempNode)
    println()
    inOrderTraversalIterative(tempNode)
    println()
    postOrderTraversalIterative(tempNode)
    println()
    levelOrderTraversal(tempNode)
    println()
}

fun preOrderTraversalRecursive(node: BNode?) {
    node?.run {
        print(" $data -->")
        preOrderTraversalRecursive(left)
        preOrderTraversalRecursive(right)
    }
}

fun inOrderTraversalRecursive(node: BNode?) {
    node?.run {
        inOrderTraversalRecursive(left)
        print(" $data -->")
        inOrderTraversalRecursive(right)
    }
}

fun postOrderTraversalRecursive(node: BNode?) {
    node?.run {
        postOrderTraversalRecursive(left)
        postOrderTraversalRecursive(right)
        print(" $data -->")
    }
}

fun preOrderTraversalIterative(node: BNode?) {
    val treeStack = Stack<BNode>()
    treeStack.push(node)
    while (treeStack.isNotEmpty()) {
        val (data, left, right) = treeStack.pop()
        print(" $data -->")
        right?.run { treeStack.push(right) }
        left?.run { treeStack.push(left) }
    }
}

fun inOrderTraversalIterative(node: BNode?) {
    val treeStack = Stack<BNode>()
    var topNode = node
    do {
        while (topNode != null) {
            treeStack.push(topNode)
            topNode = topNode.left
        }
        val (data, _, right) = treeStack.pop()
        print(" $data -->")
        topNode = right
    } while (topNode != null || treeStack.isNotEmpty())
}

fun postOrderTraversalIterative(node: BNode?) {
    val treeStack = Stack<BNode>()
    var rootNode = node
    var prevTopNode: BNode? = null

    while (rootNode != null || treeStack.isNotEmpty()) {
        while (rootNode != null) {
            treeStack.push(rootNode)
            rootNode = rootNode.left
        }
        rootNode = treeStack.peek()
        if (rootNode.right == null || rootNode.right == prevTopNode) {
            prevTopNode = treeStack.pop()
            print(" ${prevTopNode.data} -->")
            rootNode = null
        } else {
            rootNode = rootNode.right
        }
    }
}

fun levelOrderTraversal(node: BNode?) {
    val treeQueue = ArrayDeque<BNode>() as Queue<BNode>
    treeQueue.add(node)
    while (treeQueue.isNotEmpty()) {
        with(treeQueue.remove()) {
            print(" $data -->")
            treeQueue.also { queue ->
                left?.let { node -> queue.add(node) }
                right?.let { node -> queue.add(node) }
            }
        }
    }
}
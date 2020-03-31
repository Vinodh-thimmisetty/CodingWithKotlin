package coursera

import java.util.logging.Level

class VinodhRightQueue<Item> : VinodhCollection<Item> {

    private class VinodhNode<Item>(var item: Item, var next: VinodhNode<Item>? = null)

    private var headNode: VinodhNode<Item>? = null
    private var tailNode: VinodhNode<Item>? = null
    private var counter = 0L

    override fun isEmpty() = (headNode == null && tailNode == null)

    override fun size(): Long = counter

    override fun add(item: Item) {
        val newNode = VinodhNode(item)
        if (headNode == null && tailNode == null) {
            headNode = newNode
            tailNode = newNode
        } else {
            val oldHeadNode = headNode
            headNode = newNode
            headNode?.next = oldHeadNode
        }
        counter++
    }

    override fun remove() {
        // Start from Head node to prev node of tail node and move pointer of tail node to prev node.
        var startNode = headNode
        while (startNode?.next?.next != null) {
            startNode = startNode.next
        }
        startNode?.next = null
        tailNode = startNode
        counter--
    }

    override fun iterator(): Iterator<Item> {

        // First in first out -- I guess recursion is what needed
        var startNode = headNode
        var endNode = tailNode

        return object : Iterator<Item> {
            override fun hasNext() = endNode != null

            override fun next(): Item {
                val curItem = endNode?.item
                while (startNode != null && startNode?.next != endNode) {
                    startNode = startNode?.next
                }
                endNode = startNode
                startNode = headNode
                return curItem!!
            }

        }
    }
}

class VinodhLeftQueue<Item> : VinodhCollection<Item> {

    private class VinodhNode<Item>(var item: Item, var next: VinodhNode<Item>? = null)

    private var headNode: VinodhNode<Item>? = null
    private var tailNode: VinodhNode<Item>? = null
    private var counter = 0L

    override fun isEmpty() = headNode == null

    override fun size(): Long = counter

    override fun add(item: Item) {
        val oldTailNode = tailNode
        tailNode = VinodhNode(item)
        if (isEmpty()) headNode = tailNode
        oldTailNode?.next = tailNode
        counter++
    }

    override fun remove() {
        if (isEmpty()) tailNode = null
        headNode = headNode?.next
        counter--
    }

    override fun iterator(): Iterator<Item> {

        // First in first out -- I guess recursion is what needed
        var startNode = headNode

        return object : Iterator<Item> {
            override fun hasNext() = startNode != null

            override fun next(): Item {
                val curItem = startNode?.item
                startNode = startNode?.next
                return curItem!!
            }

        }
    }
}


fun main() {
    logger.level = Level.INFO
    val vinodhLeftQueue = VinodhLeftQueue<String>()
    val vinodhRightQueue = VinodhRightQueue<String>()
    vinodhLeftQueue.apply {
        add("to")
        add("be")
        add("or")
        add("not")
        add("to")
        logger.info("Current Queue Size : ${size()}")
        if (!isEmpty()) remove()
        add("be")
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        add("that")
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        add("is")
        logger.info("Current Queue Size : ${size()}")
    }
    vinodhRightQueue.apply {
        add("to")
        add("be")
        add("or")
        add("not")
        add("to")
        logger.info("Current Queue Size : ${size()}")
        if (!isEmpty()) remove()
        add("be")
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        add("that")
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        add("is")
        logger.info("Current Queue Size : ${size()}")
    }
    val iteratorLeft: Iterator<String> = vinodhLeftQueue.iterator()
    while (iteratorLeft.hasNext()) {
        print("${iteratorLeft.next()} -->")
    }
    println()
    val iteratorRight: Iterator<String> = vinodhRightQueue.iterator()
    while (iteratorRight.hasNext()) {
        print("${iteratorRight.next()} -->")
    }
}
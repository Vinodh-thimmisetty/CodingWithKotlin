package coursera

import java.util.logging.Level

interface VinodhCollection<Item> : Iterable<Item> {
    fun isEmpty(): Boolean
    fun size(): Long
    fun add(item: Item)
    fun remove()
}

class VinodhStack<Item> : VinodhCollection<Item> {

    class VinodhNode<Item>(var item: Item, var next: VinodhNode<Item>? = null)

    private var headNode: VinodhNode<Item>? = null
    var size: Long = 0

    override fun isEmpty(): Boolean = headNode == null

    override fun size() = size

    override fun add(item: Item) {
        val tempNode = headNode
        headNode = VinodhNode(item)
        headNode?.next = tempNode
        size++
    }

    // Last In First Out
    override fun remove() {
        headNode = headNode?.next
        size--
    }

    override fun iterator(): Iterator<Item> {
        var currentNode = headNode
        return object : Iterator<Item> {
            override fun hasNext(): Boolean = currentNode != null
            override fun next(): Item {
                val currentItem = currentNode?.item
                currentNode = currentNode?.next
                return currentItem!!
            }
        }
    }
}

fun main() {
    logger.level = Level.INFO
    val vinodhStack = VinodhStack<String>()
    vinodhStack.apply {
        add("to")
        add("be")
        add("or")
        add("not")
        add("to")
        logger.info("Current Stack Size : $size")
        if (!isEmpty()) remove()
        add("be")
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        add("that")
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        if (!isEmpty()) remove()
        add("is")
        logger.info("Current Stack Size : ${size()}")
    }
    val iterator: Iterator<String> = vinodhStack.iterator()
    while (iterator.hasNext()) {
        print("${iterator.next()} -->")
    }
}
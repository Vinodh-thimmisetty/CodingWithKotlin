package coursera

class AggregationNode<Item>(var item: Item, var next: AggregationNode<Item>? = null)

class AggregationStack : VinodhCollection<Double> {

    private var maxStack = mutableListOf<Double>()
    private var minStack = mutableListOf<Double>()
    private var avgStack = mutableListOf<Double>()

    private var counter = 0L
    private var head: AggregationNode<Double>? = null

    override fun isEmpty(): Boolean = head == null

    override fun size(): Long = counter

    override fun add(item: Double) {
        val oldHead = head
        head = AggregationNode(item)
        head?.next = oldHead
        if (size() == 0L) {
            maxStack.add(item)
            minStack.add(item)
            avgStack.add(item)
        } else {
            if (item > maxStack.last()) maxStack.add(item)
            if (item < minStack.last()) minStack.add(item)
            val avgVal = ((size() * avgStack.last()) + item) / (size() + 1.toDouble())
            avgStack.add(avgVal)
        }

        println("Add: $item -> Current Aggregation stats - MAX: ${maxStack.last()}, MIN: ${minStack.last()}, AVG: ${avgStack.last()}")
        counter++

    }


    @ExperimentalStdlibApi
    private fun aggregationsPop() {
        val stackTop = top()
        if (size() != 0L) {
            if (stackTop == maxStack.last()) maxStack.removeLast()
            if (stackTop == minStack.last()) minStack.removeLast()
            avgStack.removeLast()
            println("Remove: $stackTop -> Current Aggregation stats - MAX: ${maxStack.last()}, MIN: ${minStack.last()}, AVG: ${avgStack.last()}")
        }
    }

    override fun remove() {
        head = head?.next
        aggregationsPop()
        counter--
    }

    override fun iterator(): Iterator<Double> {
        TODO("Not yet implemented")
    }

    private fun top(): Double? = head?.item
}

fun main() {
    val aggregationStack = AggregationStack()
    aggregationStack.apply {
        add(50.0)
        add(70.0)
        add(10.0)
        add(30.0)
        add(90.0)
        remove()
        remove()
        remove()
        add(100.0)
    }
}
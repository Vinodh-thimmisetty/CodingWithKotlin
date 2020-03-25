package trees

@Suppress("unused")
const val OVERVIEW = """
1. Representation
    a) Linked: Sparse nodes
    b) Sequential: If tree is complete/full
        For a given node K,
            i) left/right child positions = 2×K /2×K+1
            ii) parent = K/2 position
2. Traversal
    Depth First
        a) In-Order: LEFT-DATA-RIGHT
        b) Pre-Order: DATA-LEFT-RIGHT
        c) Post-Order: LEFT-RIGHT-DATA
    Breadth First
        Level Order Traversal
3. 
"""
var rootNode: BNode? = null

fun sampleTree() {
    rootNode = BNode(10)
    rootNode?.run {
        left = BNode(20)
        right = BNode(30)
        left?.run {
            left = BNode(40)
            right = BNode(50)
            left?.run {
                left = BNode(80)
                right = BNode(90)
            }
        }
        right?.run {
            left = BNode(60)
            right = BNode(70)
            left?.run {
                left = BNode(100)
            }
        }
    }
}




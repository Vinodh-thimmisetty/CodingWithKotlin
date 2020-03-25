package trees

data class BNode(
    val data: Int, var left: BNode? = null,
    var right: BNode? = null
)
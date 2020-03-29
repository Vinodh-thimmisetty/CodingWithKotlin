package coursera


fun main() {
    val bitonicArray = intArrayOf(-3, -2, 4, 6, 10, 8, 7, 1)
    bruteForceBitonic(bitonicArray.toTypedArray(), 101)
}

fun bruteForceBitonic(input: Array<Int>, searchFor: Int) {

    val arraySize = input.size
    var middle = arraySize / 2
    var left = middle - 1
    var right = middle + 1
    var pivotIndex = -1

    while (left >= 0 && right <= arraySize - 1) {
        if (input[middle] > input[left]) {
            // in increasing order
            if (input[middle] > input[right]) {
                // in decreasing order
                pivotIndex = middle
                break
            } else {
                // go for Right
                middle += (arraySize - middle) / 2
            }
        } else {
            if (input[middle] > input[right]) {
                // in decreasing order
                pivotIndex = middle
                break
            } else {
                // go for Left
                middle /= 2
            }
        }
        left = middle - 1
        right = middle + 1
    }

    val leftArray = input.sliceArray(IntRange(0, pivotIndex))

    if (binarySearch(searchFor, leftArray) == -1) {
        if (pivotIndex < arraySize) {
            val rightArray = input.sliceArray(IntRange(pivotIndex, arraySize - 1))
            if (binarySearch(searchFor, rightArray) == -1) {
                println("$searchFor doesn't exist in ${input.contentToString()}")
            } else {
                print("$searchFor found in ${rightArray.contentToString()}")
            }
        } else {
            println("$searchFor doesn't exist in ${input.contentToString()}")
        }
    } else {
        print("$searchFor found in ${leftArray.contentToString()}")
    }

}
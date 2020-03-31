package coursera

// https://www.toptal.com/developers/sorting-algorithms


class Selection {
    companion object {
        fun sort(input: CharArray): CharArray {
            val inputSize = input.size
            (0 until inputSize).forEach { outer ->
                var min = outer
                (outer until inputSize).forEach { inner ->
                    if (input[inner] < input[min]) {
                        min = inner
                    }
                    swap(input, outer, min)
                }
            }
            return input
        }

        private fun swap(input: CharArray, i: Int, j: Int) {
            input[i] = input[j].also {
                input[j] = input[i]
            }
        }
    }
}

class Insertion {
    companion object {
        fun sort(input: CharArray): CharArray {
            val inputSize = input.size
            (0 until inputSize).forEach { outer ->
                (outer downTo 0).forEach innerLoop@{ inner ->
                    if ((inner - 1) >= 0 && input[inner] < input[inner - 1]) {
                        swap(input, inner, inner - 1)
                    } else {
                        return@innerLoop
                    }
                }
            }
            return input
        }

        private fun swap(input: CharArray, i: Int, j: Int) {
            input[i] = input[j].also {
                input[j] = input[i]
            }
        }
    }
}

fun main() {
    var start = System.currentTimeMillis()
    println(
        "Selection sort  ${Selection.sort("AEELMOTRXPS".toCharArray())
            .concatToString()} took ${System.currentTimeMillis() - start} milliseconds"
    )
    start = System.currentTimeMillis()
    println(
        "Insertion sort  ${Insertion.sort("AEELMOTRXPS".toCharArray())
            .concatToString()} took ${System.currentTimeMillis() - start}"
    )
}



package utils

fun <T> List<T>.getPermutations(): List<List<T>> {
    return if (size == 2) listOf(listOf(this[0], this[1]), listOf(this[1], this[0]))
    else map { initial ->
        (this - initial).getPermutations().map { permutation -> listOf(initial) + permutation }
    }.flatten()
}

fun leastCommonMultiple(a: Int, b: Int): Int {
    return a * b / greatestCommonDivisor(a, b)
}

fun leastCommonMultiple(a: Long, b: Long): Long {
    return a * b / greatestCommonDivisor(a, b)
}

tailrec fun greatestCommonDivisor(a: Int, b: Int): Int {
    return if (b == 0) a
    else greatestCommonDivisor(b, a % b)
}

tailrec fun greatestCommonDivisor(a: Long, b: Long): Long {
    return if (b == 0L) a
    else greatestCommonDivisor(b, a % b)
}

fun <E> cartesian(lists: List<List<E>>): Sequence<List<E>> {
    return sequence {
        val counters = Array(lists.size) { 0 }
        val length = lists.fold(1) { acc, list -> acc * list.size }

        for (i in 0 until length) {
            val result = lists.mapIndexed { index, list ->
                list[counters[index]]
            }
            yield(result)
            for (pointer in lists.size - 1 downTo 0) {
                counters[pointer]++
                if (counters[pointer] == lists[pointer].size) {
                    counters[pointer] = 0
                } else {
                    break
                }
            }
        }
    }
}
import utils.println
import utils.readInput

fun main() {

    infix fun List<Int>.opens(key: List<Int>): Boolean {
        for (i in indices) {
            if (this[i] + key[i] > 5) {
                return false
            }
        }

        return true
    }

    fun List<String>.parse(): Pair<Boolean, List<Int>> {
        val isLock = first().all { it == '#' }
        val rows = drop(1).dropLast(1)
        val heights = List(5) { i ->
            List(5) { j -> rows[j][i] }.count { it == '#' }
        }

        return isLock to heights
    }

    fun part1(input: List<String>): Int {
        val groups = input.joinToString("\r\n")
                          .split("\r\n\r\n")
                          .map { it.split("\r\n") }
                          .map { it.parse() }

        val locks = groups.filter { it.first }.map { it.second }
        val keys = groups.filter { !it.first }.map { it.second }

        return keys.sumOf { key ->
            locks.count { lock -> key opens lock }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day25")
    part1(input).println()
    part2(input).println()
}

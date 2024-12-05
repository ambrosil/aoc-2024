import utils.println
import utils.readInput

fun main() {

    fun List<String>.isRightOrder(comparator: Comparator<String>) =
        this == this.sortedWith(comparator)

    fun Set<String>.comparator() =
        Comparator<String> { a, b ->
            when {
                "$a|$b" in this -> -1
                "$b|$a" in this -> 1
                else -> 0
            }
        }

    fun part1(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotEmpty() }.toSet()
        val updates = input.dropWhile { it.isNotEmpty() }.drop(1).map { it.split(",") }

        return updates
            .filter { it.isRightOrder(rules.comparator()) }
            .sumOf { it[it.size / 2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotEmpty() }.toSet()
        val updates = input.dropWhile { it.isNotEmpty() }.drop(1).map { it.split(",") }
        val comparator = rules.comparator()

        return updates
            .filterNot { it.isRightOrder(comparator) }
            .map { it.sortedWith(comparator) }
            .sumOf { it[it.size / 2].toInt() }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

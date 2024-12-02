fun main() {
    fun List<Int>.isSafe(): Boolean {
        val diffs = zipWithNext { a, b -> b - a }
        return diffs.all { it in 1..3 } || diffs.all { it in -1 downTo -3 }
    }

    fun List<Int>.remove(n: Int): List<Int> =
        filterIndexed { index, _ -> index != n }

    fun parse(input: List<String>) =
        input.map { s -> s.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }

    fun part1(input: List<String>): Int {
        return parse(input).count { it.isSafe() }
    }

    fun part2(input: List<String>): Int {
        return parse(input).count { it.indices.any { index -> it.remove(index).isSafe() } }
    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

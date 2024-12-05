import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parse(input: List<String>): List<List<Int>> =
        input.map { s -> s.split(" ").filter { it.isNotEmpty() }.map { it.toInt() } }

    fun part1(input: List<String>): Int {
        val vals = parse(input)
        val group1 = vals.map { it[0] }.sorted()
        val group2 = vals.map { it[1] }.sorted()
        return group1.zip(group2).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val vals = parse(input)
        val group1 = vals.map { it[0] }
        val group2 = vals.map { it[1] }
        return group1.sumOf { n1 -> group2.filter { n2 -> n1 == n2 }.size * n1 }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

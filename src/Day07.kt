import utils.cartesian
import utils.println
import utils.readInput

fun main() {

    fun String.calc(n1: Long, n2: Long) = when (this) {
        "+" -> n1 + n2
        "*" -> n1 * n2
        "||" -> (n1.toString() + n2.toString()).toLong()
        else -> error("wrong op $this")
    }

    operator fun List<String>.times(times: Int) =
        generateSequence { this }.take(times).toList()

    data class Operation(val result: Long, val numbers: List<Long>) {
        fun isValid(operands: List<String>): Boolean {
            return result in cartesian(operands * (numbers.size - 1)).map {
                numbers.reduceIndexed { index, acc, num ->
                    if (index == 0) acc
                    else it[index - 1].calc(acc, num)
                }
            }
        }
    }

    fun parse(input: List<String>): List<Operation> {
        return input.map { it.split(":") }.map { s -> Operation(s[0].toLong(), s[1].trim().split(" ").map { it.toLong() }) }
    }

    fun part1(input: List<String>): Long {
        return parse(input).filter { it.isValid(listOf("+", "*")) }.sumOf { it.result }
    }

    fun part2(input: List<String>): Long {
        return parse(input).filter { it.isValid(listOf("*", "+", "||")) }.sumOf { it.result }
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

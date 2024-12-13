import utils.println
import utils.readInput

fun main() {

    data class Equation(
        val a1: Double,
        val b1: Double,
        val c1: Double,
        val a2: Double,
        val b2: Double,
        val c2: Double
    )

    fun parseEquation(text: String): Equation {
        fun parseRegexToTuple(regex: Regex, text: String): Pair<Double, Double> = regex
            .find(text)
            ?.groupValues
            ?.let { it[1].toDouble() to it[2].toDouble() }
            ?: (0.0 to 0.0)

        val (a1, a2) = parseRegexToTuple(Regex("""A: X\+(\d+), Y\+(\d+)"""), text)
        val (b1, b2) = parseRegexToTuple(Regex("""B: X\+(\d+), Y\+(\d+)"""), text)
        val (c1, c2) = parseRegexToTuple(Regex("""Prize: X=(\d+), Y=(\d+)"""), text)

        return Equation(a1, b1, c1, a2, b2, c2)
    }

    fun cramer(equation: Equation): Pair<Double, Double> {
        val a1 = equation.a1
        val b1 = equation.b1
        val c1 = equation.c1

        val a2 = equation.a2
        val b2 = equation.b2
        val c2 = equation.c2

        val det = a1 * b2 - a2 * b1
        if (det == 0.0) {
            error("Il sistema non ha soluzione unica (determinante zero).")
        }

        val dx = c1 * b2 - c2 * b1
        val dy = a1 * c2 - a2 * c1

        val x = dx / det
        val y = dy / det

        return x to y
    }

    fun List<Equation>.solve() =
        map { cramer(it) }
        .filter { it.first.mod(1.0) == 0.0 && it.second.mod(1.0) == 0.0 }
        .sumOf { it.first * 3 + it.second }

    fun parse(input: List<String>) =
        input.joinToString("\r\n").split("\r\n\r\n").map { parseEquation(it) }

    fun part1(input: List<String>) =
        parse(input).solve()

    fun part2(input: List<String>) =
        parse(input)
            .map { Equation(it.a1, it.b1, it.c1 + 10000000000000, it.a2, it.b2, it.c2 + 10000000000000) }
            .solve()
            .toBigDecimal()
            .toPlainString()

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}

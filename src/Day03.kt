import utils.println
import utils.readInput

fun main() {

    val regex = "mul\\((\\d*),(\\d*)\\)".toRegex()

    fun mul(enabled: String) =
        regex.findAll(enabled).map { it.groupValues[1].toInt() * it.groupValues[2].toInt() }.sum()

    fun part1(input: List<String>) =
        mul(input.joinToString())

    fun part2(input: List<String>): Int {
        val split = input.joinToString().split("don't()")
        val enabled = split.filterIndexed { index, _ -> index > 0 }
            .map { it.split("do()") }
            .map { it.filterIndexed { index, _ -> index > 0 } }
            .joinToString() { it.joinToString() }

        return mul(split[0]) + mul(enabled)
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

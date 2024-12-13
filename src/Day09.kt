import utils.println
import utils.readInput

fun main() {

    fun parse(input: String): List<Long?> =
        input
            .windowed(2, 2, true)
            .withIndex()
            .flatMap { (index, value) ->
                List(value.first().digitToInt()) { _ -> index.toLong() } +
                List(value.getOrElse(1){ _ -> '0' }.digitToInt()) { null }
            }

    fun part1(input: List<String>): Long {
        val blocks = parse(input.first())
        val emptyBlocks = blocks.indices.filter { blocks[it] == null }.toMutableList()

        return blocks.withIndex().reversed().sumOf { (index, value) ->
            if (value != null) {
                value * (emptyBlocks.removeFirstOrNull() ?: index)
            } else {
                emptyBlocks.removeLastOrNull()
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}

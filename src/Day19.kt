import utils.println
import utils.readInput

fun main() {

    fun combos(patterns: Set<String>, model: String): Long {
        val cache: MutableMap<String, Long> = mutableMapOf("" to 1L)

        fun recurse(towel: String): Long = cache.getOrPut(towel) {
            patterns
                .filter { towel.startsWith(it) }
                .sumOf { recurse(towel.removePrefix(it)) }
        }

        return recurse(model)
    }

    fun parse(input: List<String>): Pair<Set<String>, List<String>> {
        val towels = input.first().split(", ").toSet()
        val cases = input.drop(2)
        return towels to cases
    }

    fun part1(input: List<String>): Int {
        val (towels, cases) = parse(input)
        return cases.count { combos(towels, it) > 0 }
    }

    fun part2(input: List<String>): Long {
        val (towels, cases) = parse(input)
        return cases.sumOf { combos(towels, it) }
    }

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}

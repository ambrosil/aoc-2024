fun main() {

    fun List<String>.isRightOrder(comparator: Comparator<String>) =
        this == this.sortedWith(comparator)

    fun getComparator(rules: Set<String>) =
        Comparator<String> { a, b ->
            when {
                "$a|$b" in rules -> -1
                "$b|$a" in rules -> 1
                else -> 0
            }
        }

    fun part1(input: List<String>): Int {
        val rules: Set<String> = input.takeWhile { it.isNotEmpty() }.toSet()
        val updates: List<List<String>> = input.dropWhile { it.isNotEmpty() }.drop(1).map { row -> row.split(",") }

        return updates
            .filter { it.isRightOrder(getComparator(rules)) }
            .sumOf { it[it.size / 2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val rules: Set<String> = input.takeWhile { it.isNotEmpty() }.toSet()
        val updates: List<List<String>> = input.dropWhile { it.isNotEmpty() }.drop(1).map { row -> row.split(",") }
        val comparator = getComparator(rules)

        return updates
            .filterNot { it.isRightOrder(comparator) }
            .map { it.sortedWith(comparator) }
            .sumOf { it[it.size / 2].toInt() }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

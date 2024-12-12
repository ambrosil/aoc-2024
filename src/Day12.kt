import utils.Point
import utils.matrixAsChars
import utils.println
import utils.readInput

fun main() {

    fun MutableMap<Point, Char>.findRegions(): MutableMap<Point, Int> {
        val regions = mutableMapOf<Point, Int>()
        var regionId = -1

        for ((point, plant) in this) {
            if (point in regions) continue
            regionId++
            val queue = mutableListOf(point)

            while (queue.isNotEmpty()) {
                val current = queue.removeFirst()
                if (this[current] == plant) {
                    regions[current] = regionId
                    queue += current.getAdjacentSides().filter { it !in queue && it !in regions }
                }
            }
        }

        return regions
    }

    fun part1(input: List<String>): Int {
        val matrix = input.matrixAsChars()
        val regions = matrix.findRegions()

        return regions.map { it.value }.distinct().sumOf { id ->
            val points = regions.filter { it.value == id }.keys
            points.size * points.sumOf { point -> 4 - (point.getAdjacentSides().count { it in points }) }
        }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}

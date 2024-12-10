import utils.Point
import utils.matrix
import utils.println
import utils.readInput

fun main() {

    fun MutableMap<Point, Int>.traverse(start: Point): MutableList<Point> {
        val queue = mutableListOf(start to 0)
        val nines = mutableListOf<Point>()

        while (queue.isNotEmpty()) {
            val (point, height) = queue.removeFirst()
            if (height == 9) nines += point

            point.getAdjacentSides()
                .filter { it in this && this[it] == height + 1 }
                .forEach { queue += it to getValue(it) }
        }

        return nines
    }

    fun part1(input: List<String>): Int {
        val matrix = input.matrix()
        return matrix.entries.filter { it.value == 0 }.sumOf {
            matrix.traverse(it.key).distinct().size
        }
    }

    fun part2(input: List<String>): Int {
        val matrix = input.matrix()
        return matrix.entries.filter { it.value == 0 }.sumOf {
            matrix.traverse(it.key).size
        }
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}

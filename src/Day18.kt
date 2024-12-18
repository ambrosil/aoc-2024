import utils.*
import java.util.*

fun main() {

    val W = 70
    val H = 70

    fun List<Point>.traverse(): Int {
        val start = Point(0, 0)
        val end = Point(W, H)
        val queue = PriorityQueue<Pair<Point, Int>>(compareBy { it.second }).apply { add(start to 0) }
        val seen = mutableMapOf<Point, Int>()

        while (queue.isNotEmpty()) {
            val (location, cost) = queue.poll()
            if (location == end) {
                return cost
            } else if (seen.getOrDefault(location, Int.MAX_VALUE) > cost) {
                seen[location] = cost
                location.getAdjacentSides()
                        .filter { it !in this }
                        .filter { it.x in 0..W && it.y in 0..H }
                        .forEach { queue.add(it to cost + 1) }
            }
        }

        return -1
    }

    fun parse(input: List<String>) =
        input.map { it.split(",") }
            .map { it[0].toInt() to it[1].toInt() }
            .map { Point(it.first, it.second) }

    fun part1(input: List<String>) =
        parse(input).take(1024).traverse()

    fun part2(input: List<String>): Point {
        val corrupted = parse(input)
        var n = 1

        while (corrupted.take(n).traverse() != -1)
            n++

        return corrupted.take(n).last()
    }

    val input = readInput("Day18")
    part1(input).println()
    part2(input).println()
}

import utils.*
import utils.Direction.EAST
import java.util.*

fun main() {

    data class Location(val positions: List<Point>, val direction: Direction) {
        fun position(): Point = positions.last()
        fun key(): Pair<Point, Direction> = position() to direction
        fun step(): Location = copy(positions = positions + (position().move(direction)))
        fun clockwise(): Location = copy(direction = direction.rotate(Turn.RIGHT))
        fun antiClockwise(): Location = copy(direction = direction.rotate(Turn.LEFT))
    }

    fun MutableMap<Point, Char>.traverse(start: Point, end: Point): Int {
        val queue = PriorityQueue<Pair<Location, Int>>(compareBy { it.second })
            .apply { add(Location(listOf(start), EAST) to 0) }
        val seen = mutableMapOf<Pair<Point, Direction>, Int>()

        while (queue.isNotEmpty()) {
            val (location, cost) = queue.poll()
            if (location.position() == end) {
                return cost
            } else if (seen.getOrDefault(location.key(), Int.MAX_VALUE) > cost) {
                seen[location.key()] = cost
                location.step().apply {
                    if (this@traverse[position()] != '#') {
                        queue.add(this to cost + 1)
                    }
                }
                queue.add(location.clockwise() to cost + 1000)
                queue.add(location.antiClockwise() to cost + 1000)
            }
        }
        throw IllegalStateException("No path to goal")
    }

    fun MutableMap<Point, Char>.traversePaths(start: Point, end: Point): Int {
        val queue = PriorityQueue<Pair<Location, Int>>(compareBy { it.second })
            .apply { add(Location(listOf(start), EAST) to 0) }
        val seen = mutableMapOf<Pair<Point, Direction>, Int>()
        val allPaths: MutableSet<Point> = mutableSetOf()
        var bestCost = Int.MAX_VALUE

        while (queue.isNotEmpty()) {
            val (location, cost) = queue.poll()

            if (cost > bestCost) {
                continue
            } else if (location.position() == end) {
                allPaths.addAll(location.positions)
                bestCost = cost
            } else if (seen.getOrDefault(location.key(), Int.MAX_VALUE) >= cost) {
                seen[location.key()] = cost
                location.step().apply {
                    if (this@traversePaths[position()] != '#') {
                        queue.add(this to cost + 1)
                    }
                }
                queue.add(location.clockwise() to cost + 1000)
                queue.add(location.antiClockwise() to cost + 1000)
            }
        }

        return allPaths.size
    }

    fun part1(input: List<String>): Int {
        val matrix = input.matrixAsChars()
        val start = matrix.filter { it.value == 'S' }.keys.first()
        val end = matrix.filter { it.value == 'E' }.keys.first()
        return matrix.traverse(start, end)
    }

    fun part2(input: List<String>): Int {
        val matrix = input.matrixAsChars()
        val start = matrix.filter { it.value == 'S' }.keys.first()
        val end = matrix.filter { it.value == 'E' }.keys.first()
        return matrix.traversePaths(start, end)
    }

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}

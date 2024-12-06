import utils.*

fun main() {

    fun parse(input: List<String>): MutableMap<Point, Char> {
        val matrix = mutableMapOf<Point, Char>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                matrix[Point(x, y)] = c
            }
        }

        return matrix
    }

    fun MutableMap<Point, Char>.traverse(obstacle: Point? = null): Pair<Set<Point>, Boolean> {
        val seen = mutableSetOf<Pair<Point, Direction>>()
        var direction = Direction.NORTH
        var position = this.keys.find { this[it] == '^' }!!

        obstacle?.let { this[it] = '#' }

        while (this[position] != null && (position to direction) !in seen) {
            seen += position to direction
            val next = position.move(direction)

            if (this[next] == '#') direction = direction.rotate(Turn.RIGHT)
            else position = next
        }

        obstacle?.let { this[it] = '.' }

        val loop = this[position] != null
        return seen.map { it.first }.toSet() to loop
    }

    fun part1(input: List<String>) =
        parse(input).traverse().first.size

    fun part2(input: List<String>): Int {
        val grid = parse(input)
        val start = grid.keys.find { grid[it] == '^' }!!

        return grid.traverse()
            .first
            .filterNot { it == start }
            .count { grid.traverse(it).second }
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

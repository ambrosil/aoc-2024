import utils.Point
import utils.println
import utils.readInput

fun main() {

    fun MutableMap<Point, Char>.isXMAS(point: Point, direction: Point): Boolean {
        var cur = point

        listOf('X', 'M', 'A', 'S').forEach { c ->
            if (this[cur] != c) return false
            cur += direction
        }

        return true
    }

    fun MutableMap<Point, Char>.isX(p: Point): Boolean {
        if (this[p] != 'A') return false

        val corners = listOf(Point(-1, -1), Point(-1, 1), Point(1, 1), Point(1, -1))
            .map { c -> this[p + c] }
            .joinToString("")

        return corners in setOf("MMSS", "MSSM", "SSMM", "SMMS")
    }

    fun parse(input: List<String>): MutableMap<Point, Char> {
        val matrix = mutableMapOf<Point, Char>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                matrix[Point(x, y)] = c
            }
        }

        return matrix
    }

    fun part1(input: List<String>): Int {
        val matrix = parse(input)
        val directions = arrayOf(Point(-1,-1), Point(0,-1), Point(1,-1), Point(-1, 0), Point(1, 0), Point(-1,1), Point(0,1), Point(1,1))
        return matrix.keys.sumOf { directions.count { direction -> matrix.isXMAS(it, direction) } }
    }

    fun part2(input: List<String>): Int {
        val matrix = parse(input)
        return matrix.keys.count { matrix.isX(it) }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

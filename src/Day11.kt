import utils.println
import utils.readInput
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

fun main() {

    val cache = mutableMapOf<Pair<Long, Int>, Long>()

    fun blink(stone: Long, n: Int): Long {
        val key = stone to n
        if (key in cache) return cache[key]!!

        if (n == 0) {
            cache[key] = 1L
            return 1
        }

        if (stone == 0L) {
            val res = blink(1, n - 1)
            cache[key] = res
            return res
        }

        val str = stone.toString()
        val nDigits = str.length
        if (nDigits > 0 && nDigits % 2 == 0) {
            val l = str.substring(0..<str.length/2).toLong()
            val r = str.substring(str.length/2..<str.length).toLong()
            val res = blink(l, n - 1) + blink(r, n - 1)
            cache[key] = res
            return res
        }

        val res = blink(stone * 2024, n - 1)
        cache[key] = res
        return res
    }

    fun parse(input: List<String>): List<Long> {
        return input.first().split(" ").map { it.toLong() }
    }

    fun part1(input: List<String>) =
        parse(input).sumOf { blink(it, 25) }

    fun part2(input: List<String>) =
        parse(input).sumOf { blink(it, 75) }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}

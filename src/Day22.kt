import utils.println
import utils.readInput

fun main() {

    fun Long.mixPrune(fn: (Long) -> Long) =
        (fn(this) xor this).mod(16777216L)

    fun Long.generateSecret(): Sequence<Long> =
        generateSequence(this) { secret ->
            secret.mixPrune { it * 64 }
                  .mixPrune { it / 32 }
                  .mixPrune { it * 2048 }
        }

    fun part1(input: List<String>) =
        input.map { it.toLong() }.sumOf { it.generateSecret().drop(2000).first() }

    fun part2(input: List<String>): Int {
        return buildMap {
            input.map { it.toLong() }
                 .map { it.generateSecret().take(2001).map { i -> (i % 10).toInt() }.toList() }
                 .forEach { sequence -> sequence
                        .windowed(5, 1)
                        .map { it.zipWithNext { first, second -> second - first } to it.last() }
                        .distinctBy { it.first }
                        .forEach { (key, value) ->
                            this[key] = (this[key] ?: 0) + value
                        }
                }
        }.maxOf { it.value }
    }

    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}

import utils.println
import utils.readInput

fun main() {

    data class Wire(val name: String, val n: Int)
    data class Gate(val in1: String, val op: String, val in2: String, val out: String) {
        fun process(state: MutableMap<String, Int>) {
            state[in1] ?: return
            state[in2] ?: return

            val n1 = state.getOrDefault(in1, -1)
            val n2 = state.getOrDefault(in2, -1)

            state[out] = when (op) {
                "AND" -> n1.and(n2)
                "OR" -> n1.or(n2)
                "XOR" -> n1.xor(n2)
                else -> -1
            }
        }
    }

    fun parse(input: List<String>): Pair<List<Wire>, List<Gate>> {
        val sezioni = input.joinToString("\r\n")
            .split("\r\n\r\n")
            .map { it.split("\r\n") }

        val wires = sezioni.first().map { it.split(": ") }.map { Wire(it[0], it[1].toInt()) }
        val gates = sezioni.last().map { it.split(" -> ") }.map {
            val ops = it[0].split(" ")
            Gate(ops[0], ops[1], ops[2], it[1])
        }

        return wires to gates
    }

    fun MutableMap<String, Int>.at(n: Int): Int? {
        return this["z${String.format("%02d", n)}"]
    }

    fun MutableMap<String, Int>.readDisplay() =
        entries.filter { it.key.startsWith("z") }
            .sortedByDescending { it.key }
            .map { it.value }
            .joinToString("")
            .toLong(2)

    fun MutableMap<String, Int>.existOneEmpty() =
        entries.filter { it.key.startsWith("z") }.map { it.value }.any { it == -1 }

    fun part1(input: List<String>): Long {
        val (wires, gates) = parse(input)
        val state = wires.associate { it.name to it.n }.toMutableMap()

        gates.filter { it.out.startsWith("z") }.forEach { state[it.out] = -1 }

        do {
            gates.forEach { it.process(state) }
        } while (state.existOneEmpty())

        return state.readDisplay()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("Day24")
    part1(input).println()
    part2(input).println()
}

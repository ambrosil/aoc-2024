import utils.println
import utils.readInput

fun main() {

    var regA: Long = 0
    var regB: Long = 0
    var regC: Long = 0

    lateinit var program: IntArray
    var ip = 0
    val output = mutableListOf<Int>()

    fun combo() =
        when (val value = program[ip++]) {
            in 0..3 -> value.toLong()
            4 -> regA
            5 -> regB
            6 -> regC
            else -> error("Invalid value: $value")
    }

    fun literal(): Int = program[ip++]

    fun execNext() {
        val opcode = program[ip++]
        when (opcode) {
            0 -> regA /= 1.shl(combo().toInt())
            1 -> regB = regB xor literal().toLong()
            2 -> regB = combo() % 8
            3 -> if (regA != 0L) ip = literal()
            4 -> {
                regB = regB xor regC
                literal()
            }
            5 -> output += (combo() % 8).toInt()
            6 -> regB = regA / 1.shl(combo().toInt())
            7 -> regC = regA / 1.shl(combo().toInt())
        }
    }

    fun execAll() {
        while (ip + 1 < program.size) {
            execNext()
        }
    }

    fun readInput(input: List<String>) {
        val (a, b, c, _, p) = input
        regA = a.drop(12).toLong()
        regB = b.drop(12).toLong()
        regC = c.drop(12).toLong()
        program = p.drop(9).split(',').map { it.toInt() }.toIntArray()
    }

    fun part1(input: List<String>): String {
        readInput(input)
        execAll()
        return output.joinToString(",")
    }

    fun part2(input: List<String>): Long {
        readInput(input)
        fun resetAndExec(a: Long) {
            regA = a
            regB = 0
            regC = 0
            ip = 0
            output.clear()
            execAll()
        }

        var count = 3
        var targetOutput = program.takeLast(count)

        var a = 0L
        while (true) {
            resetAndExec(a)
            if (output == targetOutput) {
                if (count == program.size) {
                    break
                }
                count++
                targetOutput = program.takeLast(count)
                a *= 8
            } else {
                a++
            }
        }

        return a
    }

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}
